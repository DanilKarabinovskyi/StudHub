package danyil.karabinovskyi.studenthub.common.firebase

class FirebaseMessaging {
//    fun getFirebaseToken(){
//        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
//            if (!task.isSuccessful) {
//                return@OnCompleteListener
//            }
//            val token = task.result
//        })
//    }
//    val context = LocalContext.current
//    val requestPermissionLauncher = rememberLauncherForActivityResult(
//        ActivityResultContracts.RequestPermission()
//    ) { isGranted: Boolean ->
//        getFirebaseToken()
//        if (isGranted) {
//            getFirebaseToken()
//        } else {
//            // TODO: Inform user that that your app will not show notifications.
//        }
//    }
//    if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) ==
//    PackageManager.PERMISSION_GRANTED
//    ) {
//        val a = 0
//    } else if (ActivityCompat.shouldShowRequestPermissionRationale(context as Activity,
//    Manifest.permission.POST_NOTIFICATIONS)) {
//        val a = 0
//    } else {
//        // Directly ask for the permission
//        SideEffect {
//            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
//
//        }
//    }
}