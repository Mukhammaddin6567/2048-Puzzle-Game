package uz.gita.app2048MBF.mvp.presenter

import uz.gita.app2048MBF.mvp.contract.MainContract
import uz.gita.app2048MBF.mvp.model.MainModel

class MainPresenter(private val view: MainContract.View) : MainContract.Presenter {

    private val model: MainContract.Model by lazy { MainModel() }

    init {
        init()
    }

    override fun init() {
        view.illustrateMatrix(model.getMatrixData())
    }

    override fun onClickPlay() {
        view.navigateToGameScreen()
    }

    override fun onClickAbout() {
        view.popUpAboutDialog()
    }

    override fun onClickSettings() {
        view.navigateToSettingsScreen()
    }
}