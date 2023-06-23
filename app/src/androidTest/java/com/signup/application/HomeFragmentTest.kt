package com.signup.application

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import com.signup.application.views.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@HiltAndroidTest
class HomeFragmentTest {
    @get:Rule()
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule()
    var composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun open_drawer_test() {
        val navDrawerDesc = "navigation_drawer"
        composeTestRule.onNodeWithContentDescription(navDrawerDesc).performClick()
    }
}