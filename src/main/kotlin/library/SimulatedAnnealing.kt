package library

import kotlin.math.exp
import kotlin.math.pow
import kotlin.random.Random

object SimulatedAnnealing {
    private val random = Random(0)

    @JvmInline
    value class State(val value: List<Int>) {
        fun calcEnergy(): Double {
            TODO("スコアを計算する")
        }

        fun neighbourState(): State {
            TODO("ランダムな近傍を返す")
        }
    }

    fun execute(startState: State, startTime: Long, timeLimitNano: Long): State {
        val initialState = crateInitialAns(startState)

        var state = initialState
        var energy = state.calcEnergy()
        var bestState = state
        var bestEnergy = energy

        var loopCount = 0
        var currentTime = System.nanoTime()
        var currentTemperature = currentTime / timeLimitNano.toDouble()
        while (true) {
            loopCount++
            if (loopCount == 100) {
                currentTime = System.nanoTime()
                if (currentTime - startTime >= timeLimitNano) break

                currentTemperature = currentTime / timeLimitNano.toDouble()
                loopCount = 0
            }

            val nextState = state.neighbourState()
            val nextEnergy = nextState.calcEnergy()

            if (nextEnergy < bestEnergy) {
                bestState = nextState
                bestEnergy = nextEnergy
            }

            if (random.nextDouble(0.0, 1.0) <= calcProbability(energy, nextEnergy, calcTemperature(currentTemperature))) {
                state = nextState
                energy = nextEnergy
            }
        }

        return bestState
    }

    private fun crateInitialAns(state: State): State {
        TODO("初期解を作る")
    }

    private fun calcProbability(energy: Double, nextEnergy: Double, temperature: Double): Double {
        TODO("問題に合わせてdeltaを変える")
        val delta = energy - nextEnergy // 最小化するとき。最大化はマイナスをかける
        return if (delta > 0) {
            1.0
        } else {
            exp(delta / temperature)
        }
    }

    private fun calcTemperature(t: Double): Double {
        TODO("温度を変える")
        val maxTemp = Double.MAX_VALUE // 一度の変化での最大くらい
        val minTemp = Double.MIN_VALUE // 一度の変化での最小くらい
        return maxTemp.pow(1.0 - t) * minTemp.pow(t)
    }
}