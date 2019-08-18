library(ggplot2)
require(reshape2)
library(RcppRoll)
library(ggfortify)

getwd()
setwd("R workspace/final")


#getseason function
#https://stackoverflow.com/questions/9500114/find-which-season-a-particular-date-belongs-to
getSeason <- function(DATES) {
  WS <- as.Date("2012-12-15", format = "%Y-%m-%d") # Winter Solstice
  SE <- as.Date("2012-3-15",  format = "%Y-%m-%d") # Spring Equinox
  SS <- as.Date("2012-6-15",  format = "%Y-%m-%d") # Summer Solstice
  FE <- as.Date("2012-9-15",  format = "%Y-%m-%d") # Fall Equinox
  
  # Convert dates from any year to 2012 dates
  d <- as.Date(strftime(DATES, format="2012-%m-%d"))
  
  ifelse (d >= WS | d < SE, 4,
          ifelse (d >= SE & d < SS, 1,
                  ifelse (d >= SS & d < FE, 2, 3)))
}

#data loading

train <- read.table("Train data.txt",header = TRUE, sep = ",")
train$day <- as.POSIXlt(train$Date,format = "%d/%m/%Y")$wday
train$month <- format(as.Date(train$Date), "%m")
train$season <- getSeason(train$Date)


#data partitioning in to wednesday evenings in the summer
xseason <- 2
xday <- 3
timelower <- "19:00:00"
timeupper <- "23:00:00"

summerWednesdayEvenings_tr <- train[train$season == xseason & train$day == xday & strptime(train$Time,"%H:%M:%S") >= strptime(timelower,"%H:%M:%S")
                                     & strptime(train$Time,"%H:%M:%S") < strptime(timeupper,"%H:%M:%S"),]

#compute mean global active power grouped by times of day
avgSWE_GAP_tr <- aggregate(summerWednesdayEvenings_tr$Global_active_power, list(Time=summerWednesdayEvenings_tr$Time), mean)

#compute sd global active power grouped by times of day
sdSWE_GAP_tr <- aggregate(summerWednesdayEvenings_tr$Global_active_power, list(Time=summerWednesdayEvenings_tr$Time), sd)

#order for consistency of elements
avgSWE_GAP_tr <- avgSWE_GAP_tr[order(avgSWE_GAP_tr$Time, decreasing = FALSE),]


#order for consistency of elements
sdSWE_GAP_tr <- sdSWE_GAP_tr[order(sdSWE_GAP_tr$Time, decreasing = FALSE),]


#-----------------------
print(avgSWE_GAP_tr) ## graphs look alike... this variable is different 




#compute rolling mean global active power grouped by times of day
rollingAvgSWE_GAP_tr <- roll_mean(as.matrix(avgSWE_GAP_tr[2]),7, na.rm=TRUE, fill=NA, align='right')

print(rollingAvgSWE_GAP_tr)
#compute rolling sd global active power grouped by times of day
rollingSdSWE_GAP_tr <- roll_sd(as.matrix(sdSWE_GAP_tr[2]), n = 7L, na.rm = TRUE)


#rollingAvgSWE_GAP_tr <- c(0,0,0,0,0,0,rollingAvgSWE_GAP_tr)

df <- data.frame(avg = avgSWE_GAP_tr[2],Ravg_avg = rollingAvgSWE_GAP_tr)
#df <- melt(df ,  id.vars = 'Time', variable.name = 'series')


#,sd=sdSWE_GAP_tr[2], Rsd_sd = rollingSdSWE_GAP_tr
#print(df)

plot.ts(df, main = "7 to 11pm: avg and rolling_7 avg")




#--------------------
