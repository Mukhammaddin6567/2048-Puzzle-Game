package uz.gita.app2048MBF.data.repository.impl

import timber.log.Timber
import uz.gita.app2048MBF.data.local.MySharedPreferences
import uz.gita.app2048MBF.data.repository.AppRepository
import kotlin.random.Random

class AppRepositoryImpl private constructor() : AppRepository {

    private val preference by lazy { MySharedPreferences.getInstance() }
    private var canMove: Boolean = false
    private var soundBegin: Boolean = false

    companion object {
        private var obj: AppRepository? = null
        fun init() {
            obj = AppRepositoryImpl()
        }

        fun getInstance(): AppRepository = obj!!
    }

    private var currentMatrix: Array<Array<Int>> =
        arrayOf(
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0)
        )

    private val oldMatrix: Array<Array<Int>> = arrayOf(
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0)
    )

    private val addElement = 2
    private var isWin = false
    private var currentScore = preference.currentScore

    init {
        when {
            preference.isGameSaved -> {
                initMatrix()
            }
            else -> {
                addNewElementToMatrix()
                addNewElementToMatrix()
                preference.isGameSaved = false
            }
        }
    }

    private fun initMatrix() {
        val stringMatrix = preference.matrix?.split("#")
        var index = 0
        for (i in 0 until 4) {
            for (j in 0 until 4) {
                currentMatrix[i][j] = Integer.parseInt(stringMatrix?.get(index++) ?: "0")
            }
        }
    }

    override fun checkForWin(): Boolean {
        for (i in currentMatrix.indices) {
            for (j in 0 until currentMatrix[i].size) {
                if (currentMatrix[i][j] == 2048) {
                    Timber.d("currentMatrix is 2048")
                    if (preference.bestResult < currentScore) {
                        preference.bestResult = currentScore
                    }
                    return true
                }
            }
        }
        return false
    }

    override fun saveCurrentMatrixState() {
        preference.isGameSaved = true
        val stringBuilder: StringBuilder = StringBuilder()
        for (i in currentMatrix.indices) {
            for (j in 0 until currentMatrix[i].size) {
                stringBuilder.append(currentMatrix[i][j]).append("#")
            }
        }
        stringBuilder.deleteAt(stringBuilder.lastIndex)
        preference.matrix = stringBuilder.toString()
    }

    override fun saveCurrentScore(score: Int) {
        preference.currentScore = score
        currentScore = score
    }

    private fun fillOldMatrix() {
        for (i in currentMatrix.indices) {
            for (j in 0 until currentMatrix[i].size) {
                oldMatrix[i][j] = currentMatrix[i][j]
            }
        }
    }

    override fun fillMatrixWithOldMatrix() {
        if (checkOldMatrix())
            for (i in currentMatrix.indices) {
                for (j in 0 until currentMatrix[i].size) {
                    currentMatrix[i][j] = oldMatrix[i][j]
                }
            }
    }

    private fun checkOldMatrix(): Boolean {
        for (i in currentMatrix.indices) {
            for (j in 0 until currentMatrix[i].size) {
                if (oldMatrix[i][j] != 0) {
                    return true
                }
            }
        }
        return false
    }

    override fun currentScore(): Int = currentScore

    override fun bestResult(): Int {
        return when {
            currentScore > preference.bestResult -> {
                preference.bestResult = currentScore
                currentScore
            }
            else -> preference.bestResult
        }
    }

    override fun checkForFinish(): Boolean {
        for (i in currentMatrix.indices) {
            for (j in 0 until currentMatrix[i].size) {
                if (j - 1 != -1 && currentMatrix[i][j] == currentMatrix[i][j - 1] && currentMatrix[i][j] != 0) {
                    return false
                }
                if (j + 1 != 4 && currentMatrix[i][j] == currentMatrix[i][j + 1] && currentMatrix[i][j] != 0) {
                    return false
                }
                if (i - 1 != -1 && currentMatrix[i][j] == currentMatrix[i - 1][j] && currentMatrix[i][j] != 0) {
                    return false
                }
                if (i + 1 != 4 && currentMatrix[i][j] == currentMatrix[i + 1][j] && currentMatrix[i][j] != 0) {
                    return false
                }
                if (currentMatrix[i][j] == 0) return false
            }
        }
        if (preference.bestResult < currentScore) {
            preference.bestResult = currentScore
        }
        return true
    }


    override fun fillMatrixForRestart() {
        preference.isGameSaved = false
        preference.currentScore = 0
        for (i in currentMatrix.indices) {
            for (j in 0 until currentMatrix[i].size) {
                currentMatrix[i][j] = 0
            }
        }
        currentScore = 0
        addNewElementToMatrix()
        addNewElementToMatrix()
    }


    private fun addNewElementToMatrix() {
        val coordinates = ArrayList<Pair<Int, Int>>()
        for (i in currentMatrix.indices) {
            for (j in 0 until currentMatrix[i].size) {
                if (currentMatrix[i][j] == 0) {
                    coordinates.add(Pair(i, j))
                }
            }
        }
        if (coordinates.size != 0) {
            val randomIndex = Random.nextInt(0, coordinates.size)
            currentMatrix[coordinates[randomIndex].first][coordinates[randomIndex].second] =
                addElement
        }
    }

    override fun getCurrentMatrix(): Array<Array<Int>> {
        Timber.d("getMatrixData() --- ${System.currentTimeMillis()}")
        return currentMatrix
    }

    override fun getOldMatrix(): Array<Array<Int>> {
        return oldMatrix
    }


    override fun moveLeft() {
        canMove = false
        fillOldMatrix()
        soundBegin = true
        for (i in currentMatrix.indices) {
            val amount = ArrayList<Int>()
            var bool = true
            for (j in 0 until currentMatrix[i].size) {
                if (currentMatrix[i][j] == 0) continue
                if (amount.isEmpty()) amount.add(currentMatrix[i][j])
                else {
                    if (amount.last() == currentMatrix[i][j] && bool) {
                        canMove = true
                        currentScore += amount.last() * 2
                        amount[amount.size - 1] = amount.last() * 2
                        bool = false
                    } else {
                        bool = true
                        amount.add(currentMatrix[i][j])
                    }
                }

                currentMatrix[i][j] = 0
            }
            for (j in 0 until amount.size) {
                currentMatrix[i][j] = amount[j]
            }
        }
        if (!checkForFinish() && !isWin) {
            addNewElementToMatrix()
        }
    }

    override fun moveRight() {
        canMove = false
        fillOldMatrix()
        soundBegin = true

        for (i in currentMatrix.indices) {
            val amount = ArrayList<Int>()
            var bool = true
            for (j in currentMatrix[i].size - 1 downTo 0) {
                if (currentMatrix[i][j] == 0) continue
                if (amount.isEmpty()) amount.add(currentMatrix[i][j])
                else {
                    if (amount.last() == currentMatrix[i][j] && bool) {
                        canMove = true
                        currentScore += amount.last() * 2
                        amount[amount.size - 1] = amount.last() * 2
                        bool = false
                    } else {
                        bool = true
                        amount.add(currentMatrix[i][j])
                    }
                }

                currentMatrix[i][j] = 0
            }
            for (k in 0 until amount.size) {
                currentMatrix[i][3 - k] = amount[k]
            }
        }
        if (!checkForFinish() && !isWin) {
            addNewElementToMatrix()
        }

    }

    override fun moveUp() {
        canMove = false
        fillOldMatrix()
        soundBegin = true

        for (i in currentMatrix.indices) {
            val amount = ArrayList<Int>()
            var bool = true
            for (j in 0 until currentMatrix[i].size) {
                if (currentMatrix[j][i] == 0) continue
                if (amount.isEmpty()) amount.add(currentMatrix[j][i])
                else {
                    if (amount.last() == currentMatrix[j][i] && bool) {
                        canMove = true
                        currentScore += amount.last() * 2
                        amount[amount.size - 1] = amount.last() * 2
                        bool = false
                    } else {
                        bool = true
                        amount.add(currentMatrix[j][i])
                    }
                }

                currentMatrix[j][i] = 0
            }
            for (j in 0 until amount.size) {
                currentMatrix[j][i] = amount[j]
            }
        }
        if (!checkForFinish() && !isWin) {
            addNewElementToMatrix()
        }
    }

    override fun moveDown() {
        canMove = false
        fillOldMatrix()
        soundBegin = true

        for (i in currentMatrix.indices) {
            val amount = ArrayList<Int>()
            var bool = true
            for (j in currentMatrix[i].size - 1 downTo 0) {
                if (currentMatrix[j][i] == 0) continue
                if (amount.isEmpty()) amount.add(currentMatrix[j][i])
                else {
                    if (amount.last() == currentMatrix[j][i] && bool) {
                        canMove = true
                        currentScore += amount.last() * 2
                        amount[amount.size - 1] = amount.last() * 2
                        bool = false
                    } else {
                        bool = true
                        amount.add(currentMatrix[j][i])
                    }
                }

                currentMatrix[j][i] = 0
            }
            for (k in 0 until amount.size) {
                currentMatrix[3 - k][i] = amount[k]
            }
        }
        if (!checkForFinish() && !isWin) {
            addNewElementToMatrix()
        }
    }
}

