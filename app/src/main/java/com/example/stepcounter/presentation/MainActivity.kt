/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter to find the
 * most up to date changes to the libraries and their usages.
 */

package com.example.stepcounter.presentation

import android.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.Button
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.Text
import com.example.stepcounter.presentation.theme.StepCounterTheme

// Class 2
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.internal.Decoy
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StepCounterTheme {
                WearFitnessApp()
            }
        }
    }
}

@Composable
fun WearFitnessApp(){
    val navController = rememberNavController()
    var steps by remember { mutableIntStateOf(30)}
    var calories by remember { mutableIntStateOf(25) }
    var stepsGoal by remember { mutableIntStateOf(10000)}
    var caloriesGoal by remember { mutableIntStateOf(500) }

    SwipeNavigationContainer(navController = navController) {
        NavHost(
            navController = navController,
            startDestination = "progress"
        ){
            composable("progress"){
                DailyProgressScreen(
                    steps = steps,
                    calories = calories,
                    stepsGoal = stepsGoal,
                    caloriesGoal = caloriesGoal,
                    onAddStep = { steps++; calories++ }
                )
            }

            composable("heart"){
                HeartRateScreen()
            }

            composable( "goals" ){
                ModifyGoalScreen(
                    stepsGoal = stepsGoal,
                    caloriesGoal = caloriesGoal,
                    onDecreaseStepsGoal = { stepsGoal -= 500 },
                    onIncreaseStepsGoal = { stepsGoal + 500},
                    onDecreaseCaloriesGoal = { caloriesGoal -= 50},
                    onIncreaseCaloriesGoal = {caloriesGoal += 50}

                )
            }
        }
    }

}

@Composable
fun SwipeNavigationContainer(
    navController: NavHostController,
    content: @Composable () -> Unit

){
    val routes = listOf("progress", "heart", "goals")

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: "progress"
    val currentIndex = routes.indexOf(currentRoute)

    Box(
      modifier = Modifier
          .fillMaxSize()
          .background(Color.Black)
          .pointerInput(currentRoute){
              var totalDrag = 0f

              detectHorizontalDragGestures(
                  onDragStart = { totalDrag = 0f},
                  onHorizontalDrag = { change, dragAmount ->
                      change.consume()
                      totalDrag += dragAmount

              },
                  onDragEnd = {
                      if (totalDrag < -60 && currentIndex < routes.lastIndex){
                          navController.navigate(routes[currentIndex + 1]) {
                               launchSingleTop = true
                          }
                      }

                      if (totalDrag > 60 && currentIndex > 0 ){
                          navController.navigate(routes[currentIndex - 1 ]) { launchSingleTop = true}
                      }
                  }

                  )
          },
        contentAlignment = Alignment.Center
    ){
        content()
    }

}

@Composable
fun DailyProgressScreen(
    steps: Int,
    calories: Int,
    stepsGoal: Int,
    caloriesGoal: Int,
    onAddStep: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = "Daily Progress", color = Color.White, style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(10.dp))

        Text(text = "Steps", color = Color.White)
        Text(text = "$steps / $stepsGoal", color = Color.White, style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))

        Text(text = "Calories", color = Color.White)
        Text(text = "$calories / $caloriesGoal", color = Color.White, style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(12.dp))

        Button(onClick = onAddStep){
            Text("Add")
        }
        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Swipe ->", color = Color.Gray, style = MaterialTheme.typography.bodySmall)

    }
}

@Composable
fun HeartRateScreen(){
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = "Heart Rate", color = Color.White, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "72 BPM", color = Color.White, style = MaterialTheme.typography.displaySmall)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "<- ->", color = Color.Gray, style = MaterialTheme.typography.bodySmall)


    }
}

@Composable
fun ModifyGoalScreen(
    stepsGoal: Int,
    caloriesGoal: Int,
    onDecreaseStepsGoal: () -> Unit,
    onIncreaseStepsGoal: () -> Unit,
    onDecreaseCaloriesGoal: () -> Unit,
    onIncreaseCaloriesGoal: () -> Unit,
    ){
    Column(
        modifier = Modifier.fillMaxSize().padding(12.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Modify Goal", color = Color.White, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Steps", color = Color.White)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
            ){
            Button(onClick = onDecreaseStepsGoal) {Text("-") }
            Spacer (modifier = Modifier.width(6.dp))
            Text(text = stepsGoal.toString(), color = Color.White)
            Spacer (modifier = Modifier.width(6.dp))
            Button(onClick = onIncreaseStepsGoal) {Text("+") }
        }

        Spacer (modifier = Modifier.width(8.dp))

        Text(text = "Calories", color = Color.White)

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Button(onClick = onDecreaseCaloriesGoal) {Text("-") }
            Spacer (modifier = Modifier.width(6.dp))
            Text(text = stepsGoal.toString(), color = Color.White)
            Spacer (modifier = Modifier.width(6.dp))
            Button(onClick = onIncreaseCaloriesGoal) {Text("+") }
        }

        Spacer (modifier = Modifier.width(8.dp))

        Text(text = "<- Swipe", color = Color.Gray, style = MaterialTheme.typography.bodySmall)
    }
}



@Composable
fun StepCounterScreen() {
    var steps by remember {
        mutableIntStateOf(0)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Daily Goal: Hardcoded
        Text(
            text = "Daily Goal",
            color = Color.White,
            style = MaterialTheme.typography.labelMedium
        )
        Text(
            text = "10,000 steps / 500 cal",
            color = Color.White,
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.height(12.dp))

        // Calories
        Text(
            text = "Calories",
            color = Color.White,
            style = MaterialTheme.typography.labelMedium
        )
        Text(
            text = "25 kcal",
            color = Color.White,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(12.dp))

        // Steps Today: Live value
        Text(
            text = "Steps Today",
            color = Color.White,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = steps.toString(),
            color = Color.White,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(14.dp))

        Button(onClick = {
            steps ++
        }
        ) {
            Text("Add Step")
        }
    }
}