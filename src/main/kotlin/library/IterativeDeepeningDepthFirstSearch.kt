package library

import kotlin.math.max

// 反復深化。下記はアルファベータ法を時間制限いっぱい実行するだけ。
// ハッシュでメモするともっとよくなりそう。
object IterativeDeepeningDepthFirstSearch {
    var startTImeNano: Long = 0 // TODO: 実行前に代入する
    private const val TIME_LIMIT_NANO = 140000000L // TODO: 実行前に代入する

    class State {
        fun legalActions(): List<Action>  {
            TODO("実装する。lazyにしてもいいかも？")
        }

        fun advancedWith(action: Action): State {
            TODO("実装する")
        }

        fun isGameOver(): Boolean {
            TODO("実装する")
        }

        fun score(): Double {
            TODO("実装する")
        }

        fun passed(): State {
            TODO("パスが可能なら、実装する")
        }
    }

    @JvmInline
    value class Action(val value: Int)

    fun execute(state: State): Action {
        var bestAction = state.legalActions().first()
        var depth = 1
        while (true) {
            val action = bestAction(state, depth)
            if (timeOver()) break
            bestAction = action
            depth++
        }
        return bestAction
    }

    private fun bestAction(state: State, depth: Int): Action {
        val legalActions = state.legalActions()
        var bestAction = legalActions.first()
        var alpha = Double.NEGATIVE_INFINITY

        for (legalAction in legalActions) {
            val nextState = state.advancedWith(legalAction)
            val score = -negaAlpha(nextState, depth, Double.NEGATIVE_INFINITY, -alpha)
            if (timeOver()) break
            if (score > alpha) {
                alpha = score
                bestAction = legalAction
            }
        }

        return bestAction
    }

    private fun negaAlpha(state: State, depth: Int, alpha: Double, beta: Double): Double {
        if (timeOver()) return 0.0

        var tmpAlpha = alpha
        if (state.isGameOver() || depth == 0) {
            return state.score()
        }

        val legalActions = state.legalActions()
        if (legalActions.isEmpty()) {
            // TODO: パスができないゲームではスコアを返す
            val score = -negaAlpha(state.passed(), depth - 1, -beta, -tmpAlpha)
            tmpAlpha = max(tmpAlpha, score)
            if (tmpAlpha >= beta) {
                return tmpAlpha
            }
        }

        for (action in legalActions) {
            val score = -negaAlpha(state.advancedWith(action), depth - 1, -beta, -tmpAlpha)
            if (timeOver()) break
            tmpAlpha = max(tmpAlpha, score)
            if (tmpAlpha >= beta) {
                return tmpAlpha
            }
        }
        return tmpAlpha
    }

    private fun timeOver(): Boolean {
        return System.nanoTime() - startTImeNano >= TIME_LIMIT_NANO
    }
}