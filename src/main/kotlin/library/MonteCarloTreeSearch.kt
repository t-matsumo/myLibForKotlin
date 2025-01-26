package library

import kotlin.math.ln
import kotlin.math.sqrt

object MonteCarloTreeSearch {
    var startTImeNano: Long = 0 // TODO: 実行前に代入する
    var TIME_LIMIT_NANO = 140000000L // TODO: 実行前に代入する
    const val CONST_FOR_UCB1 = 1.0
    const val EXPAND_THRESHOLD = 15L // TODO: 展開するまでの訪問数を変更する

    class State {
        fun legalActions(): List<Action> {
            TODO("実装する。lazyのほうが良いかも")
        }

        fun advancedWith(action: Action): State {
            TODO("実装する")
        }

        fun isGameOver(): Boolean {
            TODO("実装する")
        }

        fun passed(): State {
            TODO("必要なら実装する")
        }

        fun randomAction(): Action? {
            TODO("実装する")
        }

        fun isLose(): Boolean {
            TODO("実装する")
        }

        fun isDraw(): Boolean {
            TODO("実装する")
        }
    }

    private class Node(
        val state: State,
        var worth: Double = 0.0,
        var childNodes: List<Node> = emptyList(),
        var visitCount: Long = 0
    ) {
        fun evaluate(): Double {
            if (state.isGameOver()) {
                val value = if (state.isLose()) 0.0 else 0.5
                this.worth += value
                visitCount++
                return value
            }
            if (childNodes.isEmpty()) {
                val value = playout(state)
                this.worth += value
                visitCount++

                if (visitCount == EXPAND_THRESHOLD) {
                    expand()
                }

                return value
            } else {
                val value = 1.0 - nextChildNode().evaluate();
                worth += value
                visitCount++
                return value
            }
        }

        fun expand() {
            val legalActions = state.legalActions()
            childNodes = if (legalActions.isEmpty()) {
                listOf(Node(state.passed())) // TODO: パスがないなら不要
            } else {
                legalActions.map { Node(state.advancedWith(it)) }
            }
        }

        private fun nextChildNode(): Node {
            for (childNode in childNodes) {
                if (childNode.visitCount == 0L) {
                    return childNode
                }
            }

            val visitCountSum = childNodes.sumOf { it.visitCount }.toDouble()
            var bestValue = Double.NEGATIVE_INFINITY
            var bestNodeIndex = 0
            for ((i, childNode) in childNodes.withIndex()) {
//                val worthRate = 1.0 - childNode.worth / childNode.visitCount
//                val bias = sqrt(2.0 * ln(visitCountSum) / childNode.visitCount)
                val ucb1 = 1.0 - childNode.worth / childNode.visitCount + CONST_FOR_UCB1 * sqrt(2.0 * ln(visitCountSum) / childNode.visitCount)
                if (ucb1 > bestValue) {
                    bestNodeIndex = i
                    bestValue = ucb1
                }
            }
            return childNodes[bestNodeIndex]
        }
    }

    @JvmInline
    value class Action(val value: Int)

    fun execute(state: State, playoutNumber: Int): Action {
        val rootNode = Node(state)
        rootNode.expand()
        for (i in 0 until playoutNumber) {
            rootNode.evaluate()

            if (timeOver()) {
                System.err.println("timeout!!!")
                break
            }
        }
        val legalActions = state.legalActions() // TODO: 空の考慮がいるかも？
        var bestVisitCount = 0L
        var bestActionIndex = 0
        for (i in legalActions.indices) {
            val visitCount = rootNode.childNodes[i].visitCount
            if (visitCount > bestVisitCount) {
                bestActionIndex = i
                bestVisitCount = visitCount
            }
        }

        return legalActions[bestActionIndex]
    }

    private fun playout(state: State): Double {
        if (state.isLose()) return 0.0
        if (state.isDraw()) return 0.5

        val action = state.randomAction()
        val nextState = if (action == null) {
            state.passed() // TODO: パスできない場合は勝敗の結果または、（1.0 - playout(nextState)）の値を返す
        } else {
            state.advancedWith(action)
        }
        return 1.0 - playout(nextState)
    }

    private fun timeOver(): Boolean {
        return System.nanoTime() - startTImeNano >= TIME_LIMIT_NANO
    }
}