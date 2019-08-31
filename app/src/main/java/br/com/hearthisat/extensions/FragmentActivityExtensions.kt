package br.com.hearthisat.extensions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import br.com.hearthisat.R

enum class FragmentAnimationType {
    SLIDE_RIGHT_TO_LEFT {
        override val enterAnimation: Int
            get() = R.anim.slide_in_from_right

        override val exitAnimation: Int
            get() = R.anim.slide_out_to_left
    },
    SLIDE_LEFT_TO_RIGHT {
        override val enterAnimation: Int
            get() = R.anim.slide_in_from_left

        override val exitAnimation: Int
            get() = R.anim.slide_out_to_right
    },
    FADE {
        override val enterAnimation: Int
            get() = android.R.anim.fade_in

        override val exitAnimation: Int
            get() = android.R.anim.fade_out
    };

    abstract val enterAnimation : Int
    abstract val exitAnimation : Int
}

enum class FragmentCommitType {
    commit,
    commitAllowingStateLoss,
    commitNow,
    commitNowAllowingStateLoss
}

fun FragmentActivity.commitFragment(layout: Int,
                                    fragment: Fragment,
                                    tag: String? = null,
                                    animationType: FragmentAnimationType? = null,
                                    commitType: FragmentCommitType = FragmentCommitType.commit,
                                    backStack: String? = null){

    val transaction = supportFragmentManager
        .beginTransaction()
        .addToBackStack(backStack)

    if (animationType != null){
        transaction.setCustomAnimations(animationType.enterAnimation, animationType.exitAnimation)
    }

    transaction.replace(layout, fragment, tag)

    when (commitType){
        FragmentCommitType.commit -> transaction.commit()
        FragmentCommitType.commitAllowingStateLoss -> transaction.commitAllowingStateLoss()
        FragmentCommitType.commitNow -> transaction.commitNow()
        FragmentCommitType.commitNowAllowingStateLoss -> transaction.commitNowAllowingStateLoss()
    }
}

fun FragmentActivity.removeFragment(tag: String){
    supportFragmentManager.findFragmentByTag(tag)?.let { fragment ->
        supportFragmentManager.beginTransaction().apply {
            detach(fragment)
            commitNow()
        }
    }
}