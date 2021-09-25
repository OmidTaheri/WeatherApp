package ir.omidtaheri.weatherapp

import android.view.LayoutInflater
import android.view.View
import ir.omidtaheri.androidbase.BaseActivity
import ir.omidtaheri.weatherapp.databinding.ActivityMainBinding


class MainActivity : BaseActivity() {

    private lateinit var viewBinding: ActivityMainBinding

    override fun inflateViewBinding(inflater: LayoutInflater): View? {
        viewBinding = ActivityMainBinding.inflate(inflater)
        return viewBinding!!.root

    }

    override fun setUp() {
        TODO("Not yet implemented")
    }

    override fun showLoading() {
        TODO("Not yet implemented")
    }

    override fun hideLoading() {
        TODO("Not yet implemented")
    }

    override fun showStringError(message: String) {
        TODO("Not yet implemented")
    }

    override fun showResError(ResId: Int) {
        TODO("Not yet implemented")
    }

    override fun showSnackBar(message: String) {
        TODO("Not yet implemented")
    }

    override fun showToast(message: String) {
        TODO("Not yet implemented")
    }

    override fun showDialog(message: String) {
        TODO("Not yet implemented")
    }

}
