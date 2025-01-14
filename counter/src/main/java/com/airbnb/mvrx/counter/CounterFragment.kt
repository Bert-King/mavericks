package com.airbnb.mvrx.counter

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.PersistState
import com.airbnb.mvrx.activityViewModel
import com.airbnb.mvrx.counter.databinding.CounterFragmentBinding
import com.airbnb.mvrx.viewbinding.viewBinding
import com.airbnb.mvrx.withState

/**
 * States 包含了所有你需要渲染到屏幕的数据
 * (State classes contain all of the data you need to render a screen)
 */
data class CounterState(@PersistState val count: Int = 0) : MavericksState

/**
 * ViewModels 是 所有业务逻辑之所在，它具有简单的生命周期，易于测试。
 * （ViewModels are where all of your business logic lives. It has a simple lifecycle and is easy to test.）
 */
class CounterViewModel(state: CounterState) : MavericksViewModel<CounterState>(state) {

    fun incrementCount() = setState { copy(count = count + 1) }
}

/**
 * Fragment在Mavericks中的作用很简单。主要是将State绑定到View上。（PS：Mavericks 适用于任何视图体系）
 *
 * 实现MavericksView的接口，然后重写invalidate()方法，实现渲染逻辑
 */
class CounterFragment : Fragment(R.layout.counter_fragment), MavericksView {
    private val binding: CounterFragmentBinding by viewBinding()
    private val viewModel: CounterViewModel by activityViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.counterText.setOnClickListener {
            viewModel.incrementCount()
        }
    }

    override fun invalidate() = withState(viewModel) { state ->
        binding.counterText.text = "${state.count}"
    }
}
