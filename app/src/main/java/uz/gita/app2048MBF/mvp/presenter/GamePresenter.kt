package uz.gita.app2048MBF.mvp.presenter

import timber.log.Timber
import uz.gita.app2048MBF.mvp.contract.GameContract
import uz.gita.app2048MBF.mvp.model.GameModel

class GamePresenter(private val view: GameContract.View) : GameContract.Presenter {
    private val model: GameContract.Model by lazy { GameModel() }
    private var currentScore = 0
    private lateinit var matrix: Array<Array<Int>>
    private lateinit var oldMatrix: Array<Array<Int>>

    override fun initGame() {
        currentScore = model.getScore()
        view.setCurrentScore(currentScore)
        view.setBestResult(model.getBestResult())
        Timber.d("current score: ${model.getScore()}")
        Timber.d("best result : ${model.getBestResult()}")
        oldMatrix = model.getOldMatrix()
        matrix = model.getMatrixData()
        oldMatrix.forEach {
            Timber.d("old matrix: ${it.toList()}")
        }
        view.illustrateMatrix(matrix)
        if (model.checkForWin()) {
            view.showWinDialog(true)
            view.setEnabledContainerCells(false)
            view.showRestartDialog(false)
            view.setEnabledUndo(false)
            view.setEnabledRestart(false)
            return
        }
        if (model.checkForFinish()) {
            view.setEnabledContainerCells(false)
            view.showGameOverDialog(true)
        }
    }

    override fun swipeLeft() {
        view.setEnabledUndo(true)
        currentScore = model.getScore()
        model.swipeLeft()
        Timber.d("model.checkForFinish(): ${model.checkForFinish()}")
        Timber.d("model.checkForWin(): ${model.checkForWin()}")
        Timber.d("current score: ${model.getScore()}")
        Timber.d("best result : ${model.getBestResult()}")
        oldMatrix = model.getOldMatrix()
        matrix = model.getMatrixData()
        oldMatrix.forEach {
            Timber.d("old matrix: ${it.toList()}")
        }
        Timber.d("\n")
        matrix.forEach {
            Timber.d("matrix: ${it.toList()}")
        }
        Timber.d("\n")
        view.illustrateMatrix(matrix)
        for (i in matrix.indices)
            for (j in matrix[i].indices)
                if (matrix[i][j] != 0 && oldMatrix[i][j] != matrix[i][j]) view.swipeLeftAnimation(i * 4 + j)

        view.setCurrentScore(model.getScore())
        view.setBestResult(model.getBestResult())
        if (model.checkForWin()) {
            view.showWinDialog(true)
            view.setEnabledContainerCells(false)
            view.showRestartDialog(false)
            view.setEnabledUndo(false)
            view.setEnabledRestart(false)
            return
        }
        if (model.checkForFinish()) {
            view.setEnabledContainerCells(false)
            view.showGameOverDialog(true)
        }
    }

    override fun swipeRight() {
        view.setEnabledUndo(true)
        currentScore = model.getScore()
        model.swipeRight()
        Timber.d("model.checkForFinish(): ${model.checkForFinish()}")
        Timber.d("model.checkForWin(): ${model.checkForWin()}")
        Timber.d("current score: ${model.getScore()}")
        Timber.d("best result : ${model.getBestResult()}")
        oldMatrix = model.getOldMatrix()
        matrix = model.getMatrixData()
        oldMatrix.forEach {
            Timber.d("old matrix: ${it.toList()}")
        }
        Timber.d("\n")
        matrix.forEach {
            Timber.d("matrix: ${it.toList()}")
        }
        Timber.d("\n")
        view.illustrateMatrix(matrix)
        for (i in matrix.indices)
            for (j in matrix[i].indices)
                if (matrix[i][j] != 0 && oldMatrix[i][j] != matrix[i][j]) view.swipeRightAnimation(i * 4 + j)
        view.setCurrentScore(model.getScore())
        view.setBestResult(model.getBestResult())
        if (model.checkForWin()) {
            view.showWinDialog(true)
            view.setEnabledContainerCells(false)
            view.showRestartDialog(false)
            view.setEnabledUndo(false)
            view.setEnabledRestart(false)
            return
        }
        if (model.checkForFinish()) {
            view.setEnabledContainerCells(false)
            view.showGameOverDialog(true)
        }
    }

    override fun swipeUp() {
        view.setEnabledUndo(true)
        currentScore = model.getScore()
        model.swipeUp()
        Timber.d("model.checkForFinish(): ${model.checkForFinish()}")
        Timber.d("model.checkForWin(): ${model.checkForWin()}")
        Timber.d("current score: ${model.getScore()}")
        Timber.d("best result : ${model.getBestResult()}")
        oldMatrix = model.getOldMatrix()
        matrix = model.getMatrixData()
        view.illustrateMatrix(matrix)
        oldMatrix.forEach {
            Timber.d("old matrix: ${it.toList()}")
        }
        Timber.d("\n")
        matrix.forEach {
            Timber.d("matrix: ${it.toList()}")
        }
        Timber.d("\n")
        for (i in matrix.indices)
            for (j in matrix[i].indices)
                if (matrix[i][j] != 0 && oldMatrix[i][j] != matrix[i][j]) view.swipeUpAnimation(i * 4 + j)
        view.setCurrentScore(model.getScore())
        view.setBestResult(model.getBestResult())
        if (model.checkForWin()) {
            view.showWinDialog(true)
            view.setEnabledContainerCells(false)
            view.showRestartDialog(false)
            view.setEnabledUndo(false)
            view.setEnabledRestart(false)
            return
        }
        if (model.checkForFinish()) {
            view.setEnabledContainerCells(false)
            view.showGameOverDialog(true)
        }
    }

    override fun swipeDown() {
        view.setEnabledUndo(true)
        currentScore = model.getScore()
        model.swipeDown()
        Timber.d("model.checkForFinish(): ${model.checkForFinish()}")
        Timber.d("model.checkForWin(): ${model.checkForWin()}")
        Timber.d("current score: ${model.getScore()}")
        Timber.d("best result : ${model.getBestResult()}")
        oldMatrix = model.getOldMatrix()
        matrix = model.getMatrixData()
        oldMatrix.forEach {
            Timber.d("old matrix: ${it.toList()}")
        }
        Timber.d("\n")
        matrix.forEach {
            Timber.d("matrix: ${it.toList()}")
        }
        Timber.d("\n")
        view.illustrateMatrix(matrix)
        for (i in matrix.indices)
            for (j in matrix[i].indices)
                if (matrix[i][j] != 0 && oldMatrix[i][j] != matrix[i][j]) view.swipeDownAnimation(i * 4 + j)
        view.setCurrentScore(model.getScore())
        view.setBestResult(model.getBestResult())
        if (model.checkForWin()) {
            view.showWinDialog(true)
            view.setEnabledContainerCells(false)
            view.showRestartDialog(false)
            view.setEnabledUndo(false)
            view.setEnabledRestart(false)
            return
        }
        if (model.checkForFinish()) {
            view.setEnabledContainerCells(false)
            view.showGameOverDialog(true)
        }
    }

    override fun onClickUndo() {
        view.setEnabledUndo(false)
        model.saveCurrentScore(currentScore)
        view.setCurrentScore(model.getScore())
        Timber.d("model.checkForFinish(): ${model.checkForFinish()}")
        if (model.checkForFinish()) {
            model.fillMatrix()
            view.illustrateMatrix(model.getMatrixData())
            view.setEnabledContainerCells(true)
            view.showGameOverDialog(false)
            view.showWinDialog(false)
            return
        }
        model.fillMatrix()
        view.illustrateMatrix(model.getMatrixData())
    }

    override fun onClickDialogRestartNo() {
        view.setEnabledContainerCells(true)
        view.showRestartDialog(false)
        view.setEnabledUndo(true)
    }

    override fun onClickDialogRestartYes() {
        model.saveCurrentScore(0)
        model.fillMatrixForRestart()
        view.showRestartDialog(false)
        view.setEnabledContainerCells(true)
        view.setEnabledUndo(true)
        restartGame()
    }

    override fun onClickDialogWinRestart() {
        view.showWinDialog(false)
        view.setEnabledContainerCells(true)
        view.setEnabledUndo(true)
        view.setEnabledRestart(true)
        restartGame()
    }

    override fun onClickRestart() {
        view.setEnabledContainerCells(false)
        view.showGameOverDialog(false)
        view.showWinDialog(false)
        view.setEnabledUndo(false)
        view.showRestartDialog(true)
    }

    override fun popBackStack() {
        view.popBackStack()
    }

    private fun restartGame() {
        model.fillMatrixForRestart()
        initGame()
    }

    override fun saveCurrentMatrixState() = kotlin.run { model.saveCurrentMatrixState() }

    override fun saveCurrentScore() = kotlin.run { model.saveCurrentScore(model.getScore()) }
}