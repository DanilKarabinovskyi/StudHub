package danyil.karabinovskyi.studenthub.core.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

interface Work<T> {

    fun execute(scope: CoroutineScope, background: suspend () -> T, ui:(T) -> Unit)

    class Base(
        private val dispatcher: Dispatcher
    ) : Work<String> {

        override fun execute(scope: CoroutineScope, background: suspend () -> String, ui: (String) -> Unit) {
            dispatcher.doBackground(scope) {
                val result = background.invoke()
                dispatcher.doUi(scope) {
                    ui.invoke(result)
                }
            }
        }

    }
}

interface Dispatcher {

    fun doBackground(scope: CoroutineScope,block: suspend () -> Unit)

    fun doUi(scope: CoroutineScope,block: suspend () -> Unit)

    class Base(
        private val dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) : Dispatcher {

        override fun doBackground(scope: CoroutineScope, block: suspend () -> Unit) {
            scope.launch(dispatcher) {
                block.invoke()
            }
        }

        override fun doUi(scope: CoroutineScope,block: suspend () -> Unit) {
            scope.launch(Dispatchers.Main) {
                block.invoke()
            }
        }
    }
}