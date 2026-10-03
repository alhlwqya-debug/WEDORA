package com.wedora.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.core.content.ContextCompat
import androidx.compose.ui.unit.LayoutDirection

private val Gold = Color(0xFFD4AF37)
private val Black = Color(0xFF1A1A1A)
private val Ivory = Color(0xFFFAF8F3)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { WedoraTheme { LoginScreen() } }
    }
    fun showBiometric() {
        val prompt = BiometricPrompt(this, ContextCompat.getMainExecutor(this), object : BiometricPrompt.AuthenticationCallback() {})
        prompt.authenticate(BiometricPrompt.PromptInfo.Builder().setTitle("تسجيل الدخول إلى WEDORA").setSubtitle("استخدم بصمة الإصبع للمتابعة").setNegativeButtonText("إلغاء").build())
    }
}

@Composable
fun WedoraTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = lightColorScheme(primary = Gold, onPrimary = Black, background = Ivory, surface = Color.White, onSurface = Black), content = content)
}

@Composable
fun LoginScreen() {
    val activity = LocalContext.current as MainActivity
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var visible by remember { mutableStateOf(false) }
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Surface(Modifier.fillMaxSize(), color = Ivory) {
            Column(Modifier.fillMaxSize().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Text("WEDORA", color = Gold, fontSize = 42.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
                Text("لتعهدات الحفلات", color = Black.copy(alpha = .7f))
                Spacer(Modifier.height(28.dp))
                Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(28.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(3.dp)) {
                    Column(Modifier.padding(22.dp)) {
                        Text("تسجيل الدخول", Modifier.fillMaxWidth(), textAlign = TextAlign.Right, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(18.dp))
                        OutlinedTextField(phone, { phone = it }, Modifier.fillMaxWidth(), singleLine = true, label = { Text("رقم الجوال") }, placeholder = { Text("05xxxxxxxx") }, shape = RoundedCornerShape(16.dp))
                        Spacer(Modifier.height(12.dp))
                        OutlinedTextField(password, { password = it }, Modifier.fillMaxWidth(), singleLine = true, label = { Text("كلمة المرور") }, leadingIcon = { Icon(Icons.Default.Lock, null) }, visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(), trailingIcon = { IconButton({ visible = !visible }) { Icon(if (visible) Icons.Default.VisibilityOff else Icons.Default.Visibility, null) } }, shape = RoundedCornerShape(16.dp))
                        TextButton({ }, Modifier.fillMaxWidth().wrapContentWidth(Alignment.End)) { Text("نسيت كلمة المرور؟", color = Gold) }
                        Button({ }, Modifier.fillMaxWidth().height(54.dp), shape = RoundedCornerShape(16.dp), colors = ButtonDefaults.buttonColors(containerColor = Gold, contentColor = Black)) { Text("تسجيل الدخول", fontWeight = FontWeight.Bold) }
                        Spacer(Modifier.height(10.dp))
                        OutlinedButton({ activity.showBiometric() }, Modifier.fillMaxWidth().height(52.dp), shape = RoundedCornerShape(16.dp)) { Icon(Icons.Default.Fingerprint, null); Spacer(Modifier.width(8.dp)); Text("الدخول بالبصمة") }
                    }
                }
            }
        }
    }
}
