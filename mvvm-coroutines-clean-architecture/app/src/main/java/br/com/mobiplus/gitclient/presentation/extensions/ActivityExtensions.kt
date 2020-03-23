package br.com.mobiplus.gitclient.presentation.extensions

import android.app.Activity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

fun Activity.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Activity.isValidForGlide(): Boolean = !(this.isDestroyed || this.isFinishing)

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransaction { add(frameId, fragment) }
}


fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransaction { replace(frameId, fragment) }
}

fun Fragment.showToast(message: String) {
    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.loadImage(url: String, component: CircleImageView) {
    Glide.with(this)
        .load(url)
        .placeholder(br.com.mobiplus.gitclient.R.drawable.ic_person)
        .error(br.com.mobiplus.gitclient.R.drawable.ic_person)
        .into(component)
}
