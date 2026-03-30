package com.gemini.aichatbot.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sem8mychatbot.databinding.ItemOnboardingBinding
import com.gemini.aichatbot.databinding.ItemOnboardingBinding
import com.gemini.aichatbot.models.OnboardingPage

/**
 * Adapter for the onboarding ViewPager2.
 * Each page shows an animation, title, and description.
 */
class OnboardingAdapter(
    private val pages: List<OnboardingPage>
) : RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
        val binding = ItemOnboardingBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return OnboardingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        holder.bind(pages[position])
    }

    override fun getItemCount() = pages.size

    inner class OnboardingViewHolder(
        private val binding: ItemOnboardingBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(page: OnboardingPage) {
            binding.apply {
                tvTitle.text = page.title
                tvDescription.text = page.description
                // Load Lottie animation from raw resources
                lottieView.setAnimation(page.lottieAnimation)
                lottieView.playAnimation()
            }
        }
    }
}