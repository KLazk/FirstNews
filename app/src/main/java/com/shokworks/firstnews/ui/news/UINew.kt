package com.shokworks.firstnews.ui.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.shokworks.firstnews.R
import com.shokworks.firstnews.databinding.FragmentUinewBinding
import com.shokworks.firstnews.network.entitys.Article
import com.shokworks.firstnews.providers.*
import com.shokworks.firstnews.ui.NavigationActivity
import com.shokworks.firstnews.viewModels.MainViewModel
import com.shokworks.firstnews.viewModels.NavViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_navigation.*
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class UINew : Fragment() {

    private lateinit var _binding: FragmentUinewBinding
    private val binding get() = _binding
    private lateinit var activity: NavigationActivity

    /** Inject ViewModel */
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var navViewModel: NavViewModel
    @Inject lateinit var dialog: Dialog
    @Inject lateinit var constructObject: ConstructObject

    private val adapterNews: AdapterNews by lazy {
        AdapterNews(
            requireContext(),
            viewModel,
            dialog,
            arrayListOf()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentUinewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (getActivity() as NavigationActivity).also { activity = it }

        navViewModel = ViewModelProvider(requireActivity())[NavViewModel::class.java]
        binding.idSwipeRefresh.setOnRefreshListener {
            binding.idSwipeRefresh.isRefreshing = true
            requestNews(isRefreshing = true)
        }

        /** Adapter RecyclerView */
        binding.rvNews.layoutManager = LinearLayoutManager(requireContext())
        binding.rvNews.adapter = adapterNews

        requestNews(isRefreshing = false)
        viewModel.score.observe(viewLifecycleOwner) { itemNew ->
            if (itemNew.isNotEmpty()){
                adapterNews.setNews(itemNew as ArrayList<Article>)
                binding.rvNews.onItemClick { _, p, _ ->
                    activity.idIconFav.visibility = View.VISIBLE
                    if (itemNew[p].isFav) activity.idIconFav.setImageResource(R.drawable.ic_fav_true_24) else activity.idIconFav.setImageResource(R.drawable.ic_fav_false_24)
                    activity.idTitle.visibility = View.GONE
                    navViewModel.sendNavArticle(itemNew[p])
                    findNavController().navigate(R.id.action_nav_UITabs_to_nav_DetailsNews)
                }
            }
        }

        /** Obtener comportamiento de las noticias en el LiveData */
        navViewModel.new.observe(viewLifecycleOwner) {
            viewModel.updIsFav(it, it.isFav)
        }
    }

    /** Request de las Noticias recientes */
    private fun requestNews(
        isRefreshing: Boolean
    ){
        viewModel.vmGetNews(
            q = "apple",
            from = "2022-07-11",
            to = timeCurrent(),
            sortBy = "popularity",
            onSuccess = {
                Timber.e("News: $it")
                if (isRefreshing) binding.idSwipeRefresh.isRefreshing = false
                    if (it?.articles!!.isNotEmpty()){
                        binding.rvNews.visibility = View.VISIBLE
                        binding.idInfoRv.visibility = View.GONE
                        viewModel.setArticle(itemsNew = it)
                    } else {
                        binding.rvNews.visibility = View.GONE
                        binding.idInfoRv.visibility = View.VISIBLE
                        binding.idInfoRv.text = requireContext().getString(R.string.SinNoticias)
                    }
            },
            onFailure = { error, code ->
                if (isRefreshing) binding.idSwipeRefresh.isRefreshing = false
                NetworkResponseHttp.e(
                    context = requireContext(),
                    code = code,
                    mensaje = error,
                    codeHttp = { mensaje, descripcion ->
                        dialog.menssage(
                            context = requireContext(),
                            mensaje = mensaje,
                            descripcion = descripcion,
                            boolean = true,
                            refresh = {
                                if (it) {
                                    requestNews( isRefreshing = false)
                                }
                            }
                        )
                    }
                )
            }
        )
    }
}