package uz.gita.app2048MBF.mvp.contract

interface MainContract {

    interface Model {
        fun getMatrixData(): Array<Array<Int>>
    }

    interface View {
        fun illustrateMatrix(matrix: Array<Array<Int>>)
        fun navigateToGameScreen()
        fun popUpAboutDialog()
        fun navigateToSettingsScreen()
    }

    interface Presenter {
        fun init()
        fun onClickPlay()
        fun onClickAbout()
        fun onClickSettings()
    }

}