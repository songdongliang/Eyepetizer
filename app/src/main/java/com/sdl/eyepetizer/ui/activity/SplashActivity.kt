package com.sdl.eyepetizer.ui.activity

import android.Manifest
import android.content.Intent
import android.graphics.Typeface
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import com.orhanobut.logger.Logger
import com.sdl.eyepetizer.EyepetizerApplication
import com.sdl.eyepetizer.R
import com.sdl.eyepetizer.showToast
import com.sdl.eyepetizer.util.AppUtil
import kotlinx.android.synthetic.main.activity_splash.*
import me.weyye.hipermission.HiPermission
import me.weyye.hipermission.PermissionCallback
import me.weyye.hipermission.PermissionItem

class SplashActivity : BaseActivity() {

    private var textTypeface: Typeface? = null

    private var descTypeface: Typeface? = null

    private var alphaAnimation: AlphaAnimation? = null

    init {
        textTypeface = Typeface.createFromAsset(EyepetizerApplication.context.assets,"fonts/Lobster-1.4.otf")
        descTypeface = Typeface.createFromAsset(EyepetizerApplication.context.assets,"fonts/FZLanTingHeiS-L-GB-Regular.TTF")
    }

    override fun layoutId(): Int {
        return R.layout.activity_splash
    }

    override fun initData() {

    }

    override fun initView() {
        text_app_name.typeface = textTypeface
        text_splash_desc.typeface = descTypeface
        text_version_name.text = "v${AppUtil.getVersionName(EyepetizerApplication.context)}"

        //渐变展示启动屏
        alphaAnimation = AlphaAnimation(0.3f,1.0f)
        alphaAnimation?.duration = 2000
        alphaAnimation?.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationRepeat(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                enterMainPage()
            }

            override fun onAnimationStart(animation: Animation?) {

            }

        })
        findViewById<View>(android.R.id.content).startAnimation(alphaAnimation)
        checkPermission()
    }

    private fun enterMainPage() {
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }

    override fun start() {

    }

    /**
     * 部分厂商修改了6.0系统申请机制,他们修改成系统自动申请权限了
     */
    private fun checkPermission() {
        var permissionItems = ArrayList<PermissionItem>()
        permissionItems.add(PermissionItem(Manifest.permission.READ_PHONE_STATE,"手机状态",R.drawable.permission_ic_phone))
        permissionItems.add(PermissionItem(Manifest.permission.WRITE_EXTERNAL_STORAGE,"存储空间",R.drawable.permission_ic_storage))
        HiPermission.create(this)
                .title(getString(R.string.dear_user))
                .msg(getString(R.string.permission_apply))
                .permissions(permissionItems)
                .style(R.style.PermissionDefaultBlueStyle)
                .animStyle(R.style.PermissionAnimScale)
                .checkMutiPermission(object : PermissionCallback{
                    override fun onFinish() {
                        showToast(getString(R.string.init_finish))
                        Logger.i("permission apply finish")
                    }

                    override fun onDeny(permission: String?, position: Int) {
                        //权限拒绝
                        Logger.i("deny %s permission",permission)
                    }

                    override fun onGuarantee(permission: String?, position: Int) {
                        //权限申请成功
                        Logger.i("guarantee %s permission",permission)
                    }

                    override fun onClose() {
                        Logger.i("permission_onClose")
                        showToast(getString(R.string.user_close_permission))
                    }

                })
    }
}
