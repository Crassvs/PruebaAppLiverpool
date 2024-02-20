package com.example.pruebaappliverpool

import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.testing.TestNavHostController
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.pruebaappliverpool.presentation.view.MainActivity
import com.example.pruebaappliverpool.presentation.view.navigation.AppNavigation
import com.example.pruebaappliverpool.presentation.view.order.OrderProductActivity
import com.example.pruebaappliverpool.presentation.view.product.ProductActivity
import com.example.pruebaappliverpool.presentation.view.product.SearchView
import com.example.pruebaappliverpool.presentation.viewmodel.ProductViewModel
import com.example.pruebaappliverpool.presentation.theme.PruebaLiverpoolTheme

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    val composeTestRule  = createAndroidComposeRule<MainActivity>()
    private lateinit var navController: TestNavHostController

    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.pruebaappliverpool", appContext.packageName)
    }

    @Test
    fun greeting_composable_component_test(){
        composeTestRule.activity.setContent {
            PruebaLiverpoolTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        AppNavigation()
                    }
                }
            }
        }
    }

    @Test
    fun searchViewTest(){
        composeTestRule.activity.setContent { SearchView(
            navController = navController
        )}
    }

    @Test
    fun productViewTest(){
        composeTestRule.activity.setContent {
            val testmodel = composeTestRule.activity.viewModels<ProductViewModel>().value
            ProductActivity(navController = navController, productViewModel = testmodel)
        }
    }

    @Test
    fun orderViewTest(){
        composeTestRule.activity.setContent {
            val testmodel = composeTestRule.activity.viewModels<ProductViewModel>().value
            OrderProductActivity(navController = navController, productViewModel = testmodel, "xbox")
        }
    }

}