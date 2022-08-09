package uz.gita.app2048MBF.mvp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import timber.log.Timber
import uz.gita.app2048MBF.R
import uz.gita.app2048MBF.databinding.FragmentMainBinding
import uz.gita.app2048MBF.mvp.contract.MainContract
import uz.gita.app2048MBF.mvp.presenter.MainPresenter
import uz.gita.app2048MBF.utils.getBackgroundByValue


class MainScreen : Fragment(R.layout.fragment_main), MainContract.View {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val presenter: MainContract.Presenter by lazy { MainPresenter(this) }
    private val buttons: MutableList<TextView> by lazy { ArrayList() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        subscribeViewBinding()
    }

    private fun subscribeViewBinding() = with(binding) {
        btnPlay.setOnClickListener {
            presenter.onClickPlay()
        }
        /*buttonAbout.setOnClickListener {
            presenter.onClickAbout()
        }
        buttonSettings.setOnClickListener {
            presenter.onClickSettings()
        }*/
        for (i in 0 until cellsContainer.childCount) {
            val line: LinearLayoutCompat = cellsContainer.getChildAt(i) as LinearLayoutCompat
            for (j in 0 until line.childCount) {
                buttons.add(line.getChildAt(j) as TextView)
            }
        }
    }

    override fun illustrateMatrix(matrix: Array<Array<Int>>) {
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                if (matrix[i][j] == 0) {
                    buttons[i * 4 + j].text = ""
                    buttons[i * 4 + j].setBackgroundResource(matrix[i][j].getBackgroundByValue())
                } else {
                    buttons[i * 4 + j].text = matrix[i][j].toString()
                    buttons[i * 4 + j].setBackgroundResource(matrix[i][j].getBackgroundByValue())
                }
            }
        }
    }

    override fun navigateToGameScreen() {
        findNavController().navigate(R.id.action_mainScreen_to_gameScreen)
    }

    override fun popUpAboutDialog() {
    }

    override fun navigateToSettingsScreen() {
//        openScreenSaveStack()
    }

    override fun onResume() {
        super.onResume()
        Timber.d("onResume()")
        Timber.d("buttons: ${buttons.size}")
        presenter.init()
    }

    override fun onPause() {
        super.onPause()
        buttons.clear()
    }
}