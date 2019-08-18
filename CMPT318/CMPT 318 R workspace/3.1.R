library("depmixS4")
library(ggplot2)
library(xts)

# getwd()
setwd("R workspace/final")
data <- read.table("Train Data.txt",header = TRUE, sep = ",")
#data$day <- as.POSIXlt(data$Date,format = "%d/%m/%Y")$wday
data$DateTime <- as.POSIXct(paste(data$Date, data$Time), format="%d/%m/%Y %H:%M:%S", tz="GMT")

# Geometric mean
#Stackoverflow - https://stackoverflow.com/questions/2602583/geometric-mean-is-there-a-built-in
gm_mean = function(x, na.rm=TRUE){
  #exp(sum(log(x[x>0]), na.rm=na.rm) / length(x))
  exp(sum(log(x), na.rm=na.rm) / length(x))
}

# Mode function - https://www.tutorialspoint.com/r/r_mean_median_mode.htm
getmode <- function(v) {
  uniqv <- unique(v)
  uniqv[which.max(tabulate(match(v, uniqv)))]
}

slope_calc <- function(x, y){
  mean_x = mean(x)
  mean_y = mean(y)
  mean_y / mean_x
}

#hourly.xts <- xts(data, order.by = as.POSIXlt(paste(data$Date, data$Time), format="%d/%m/%Y %H:%M:%S"))

xtsDF <- xts(data, order.by = as.POSIXct(data$DateTime, format="%y-%m-%d %H:%M:%S"))


hourlyDF <- period.apply(xtsDF[,3:9], endpoints(xtsDF, "hours"), FUN=mean, , na.rm=TRUE)
dailyDF <- apply.daily(xtsDF[,3:9], mean, na.rm=TRUE)
weeklyDF <- apply.weekly(xtsDF[,3:9], mean, na.rm=TRUE)
monthlyDF <- apply.monthly(xtsDF[,3:9], mean, na.rm=TRUE)

hourlyDF$Mean_time <- NA
hourlyDF$Mean_active_power <- NA

for ( i in 1:nrow(hourlyDF)){
  current_date = as.POSIXlt(index(hourlyDF[i]))
  mean_time = current_date$hour - 11
  hourlyDF$Mean_time[i] = mean_time * mean_time
  hourlyDF$Mean_active_power[i] = mean_time * (hourlyDF$Global_active_power[i] - coredata(dailyDF$Global_active_power[index(current_date),]))
}

dailyDF$Mean_active_power <- apply.daily(hourlyDF[,8:9], mean, na.rm=TRUE)
dailyDF$Linear_regression <- dailyDF$Mean_active_power / dailyDF$Mean_time


split_monthlyDF <- split(monthlyDF, f="months", k="12", drop=FALSE)

autoplot(split_monthlyDF[[2]], ylim= c(0,10))
autoplot(monthlyDF, ylim = c(0, 10))