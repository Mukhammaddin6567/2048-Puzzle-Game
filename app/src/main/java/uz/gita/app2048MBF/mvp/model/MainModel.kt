package uz.gita.app2048MBF.mvp.model

import uz.gita.app2048MBF.data.repository.AppRepository
import uz.gita.app2048MBF.data.repository.impl.AppRepositoryImpl
import uz.gita.app2048MBF.mvp.contract.MainContract

class MainModel : MainContract.Model {

    private val repository: AppRepository by lazy { AppRepositoryImpl.getInstance() }

    override fun getMatrixData(): Array<Array<Int>> = repository.getCurrentMatrix()
}