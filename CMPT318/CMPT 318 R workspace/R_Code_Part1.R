data("CO2")
d <- CO2
class(d)
head(d)
d

#####################

getwd()
setwd("/Users/Amir/Desktop/")
df <- read.csv("Trajectory.csv", header = TRUE)
class(df)
head(df)

df[10,3]
df[10,]
df[,3]



off <- df[df$STATUS == "OFF",]
on <- df[df$STATUS == "ON",]

class(off)
head(off)
print(off$STATUS)

print(off$SPEED) # Speed of the car is zero when its off!!! 
print(off$DAY.NIGHT)

avgSpeed <- mean(df$SPEED)
print(avgSpeed)

overSpeedNight <- df[df$SPEED > 75 & df$DAY.NIGHT == "Night",]
nrow(overSpeedNight)

overSpeedDay <- df[df$SPEED > 75 & df$DAY.NIGHT == "Day",]
nrow(overSpeedDay)

print(mean(df$SPEED[df$DAY.NIGHT == "Night"]))
print(mean(df$SPEED[df$DAY.NIGHT == "Day"]))

avgHeading <- mean(df$HEADING)
night <- df[df$DAY.NIGHT=="Night",]

changInHeadingNight <- 0
counterNight <- 0
for(heading in night$HEADING) {
  changInHeadingNight = changInHeadingNight + abs(heading - avgHeading)
  counterNight = counterNight + 1
}
changInHeadingNight/counterNight

day <- df[df$DAY.NIGHT=="Day",]
changInHeadingDay <- 0
counterDay <- 0
for(heading in headingDay$HEADING) {
  changInHeadingDay = changInHeadingDay + abs(heading - avgHeading)
  counterDay = counterDay + 1
}
changInHeadingDay/counterDay