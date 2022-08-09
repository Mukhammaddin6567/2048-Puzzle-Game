package uz.gita.app2048MBF.mvp.model

import uz.gita.app2048MBF.data.repository.AppRepository
import uz.gita.app2048MBF.data.repository.impl.AppRepositoryImpl
import uz.gita.app2048MBF.mvp.contract.GameContract

class GameModel : GameContract.Model {

    private val repository: AppRepository by lazy { AppRepositoryImpl.getInstance() }

    override fun getMatrixData(): Array<Array<Int>> = repository.getCurrentMatrix()

    override fun getOldMatrix(): Array<Array<Int>> =repository.getOldMatrix()

    override fun swipeLeft() = kotlin.run { repository.moveLeft() }

    override fun swipeRight() = run { repository.moveRight() }

    override fun swipeUp() = run { repository.moveUp() }

    override fun swipeDown() = run { repository.moveDown() }

    override fun getScore(): Int = repository.currentScore()

    override fun getBestResult(): Int = repository.bestResult()

    override fun fillMatrix() = kotlin.run { repository.fillMatrixWithOldMatrix() }

    override fun fillMatrixForRestart() = kotlin.run { repository.fillMatrixForRestart() }

    override fun saveCurrentMatrixState() = kotlin.run { repository.saveCurrentMatrixState() }

    override fun checkForFinish(): Boolean = repository.checkForFinish()

    override fun checkForWin(): Boolean = repository.checkForWin()

    override fun saveCurrentScore(score:Int) = kotlin.run { repository.saveCurrentScore(score) }

}