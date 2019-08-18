library(ggplot2)
require(reshape2)
library(RcppRoll)
library(ggfortify)

getwd()
setwd("/home/darienubuntu/cmpt318/FinalProject")


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

train <- read.table("Train Data.txt",header = TRUE, sep = ",")
train$day <- as.POSIXlt(train$Date,format = "%d/%m/%Y")$wday
train$month <- format(as.Date(train$Date), "%m")
train$season <- getSeason(train$Date)


#data partitioning in to wednesday evenings in the summer
xseason <- 1
xxseason <- 2
xxxseason <- 3
xxxxseason <- 4
xday <- 3
timelower <- "19:00:00"
timeupper <- "23:00:00"

springWednesdayEvenings_tr <- train[train$season == xseason & train$day == xday & strptime(train$Time,"%H:%M:%S") >= strptime(timelower,"%H:%M:%S")
                                     & strptime(train$Time,"%H:%M:%S") < strptime(timeupper,"%H:%M:%S"),]
summerWednesdayEvenings_tr <- train[train$season == xxseason & train$day == xday & strptime(train$Time,"%H:%M:%S") >= strptime(timelower,"%H:%M:%S")
                                    & strptime(train$Time,"%H:%M:%S") < strptime(timeupper,"%H:%M:%S"),]
fallWednesdayEvenings_tr <- train[train$season == xxxseason & train$day == xday & strptime(train$Time,"%H:%M:%S") >= strptime(timelower,"%H:%M:%S")
                                    & strptime(train$Time,"%H:%M:%S") < strptime(timeupper,"%H:%M:%S"),]
winterWednesdayEvenings_tr <- train[train$season == xxxxseason & train$day == xday & strptime(train$Time,"%H:%M:%S") >= strptime(timelower,"%H:%M:%S")
                                    & strptime(train$Time,"%H:%M:%S") < strptime(timeupper,"%H:%M:%S"),]

#compute mean global active power grouped by times of day
summeravgSWE_GAP_tr <- aggregate(summerWednesdayEvenings_tr$Global_active_power, list(Time=summerWednesdayEvenings_tr$Time), mean)
springavgSWE_GAP_tr <- aggregate(springWednesdayEvenings_tr$Global_active_power, list(Time=springWednesdayEvenings_tr$Time), mean)
fallavgSWE_GAP_tr <- aggregate(fallWednesdayEvenings_tr$Global_active_power, list(Time=fallWednesdayEvenings_tr$Time), mean)
winteravgSWE_GAP_tr <- aggregate(winterWednesdayEvenings_tr$Global_active_power, list(Time=winterWednesdayEvenings_tr$Time), mean)

#compute sd global active power grouped by times of day
#sdSWE_GAP_tr <- aggregate(summerWednesdayEvenings_tr$Global_active_power, list(Time=summerWednesdayEvenings_tr$Time), sd)

#order for consistency of elements
summeravgSWE_GAP_tr <- summeravgSWE_GAP_tr[order(summeravgSWE_GAP_tr$Time, decreasing = FALSE),]
springavgSWE_GAP_tr <- springavgSWE_GAP_tr[order(springavgSWE_GAP_tr$Time, decreasing = FALSE),]
fallavgSWE_GAP_tr <- fallavgSWE_GAP_tr[order(fallavgSWE_GAP_tr$Time, decreasing = FALSE),]
winteravgSWE_GAP_tr <- winteravgSWE_GAP_tr[order(winteravgSWE_GAP_tr$Time, decreasing = FALSE),]


#order for consistency of elements
#sdSWE_GAP_tr <- sdSWE_GAP_tr[order(sdSWE_GAP_tr$Time, decreasing = FALSE),]


#-----------------------
#print(avgSWE_GAP_tr) ## graphs look alike... this variable is different 




#compute rolling mean global active power grouped by times of day
summerrollingAvgSWE_GAP_tr <- roll_mean(as.matrix(summerWednesdayEvenings_tr[3]),7, na.rm=TRUE, fill=NA, align='right')
springrollingAvgSWE_GAP_tr <- roll_mean(as.matrix(springWednesdayEvenings_tr[3]),7, na.rm=TRUE, fill=NA, align='right')
fallrollingAvgSWE_GAP_tr <- roll_mean(as.matrix(fallWednesdayEvenings_tr[3]),7, na.rm=TRUE, fill=NA, align='right')
winterrollingAvgSWE_GAP_tr <- roll_mean(as.matrix(winterWednesdayEvenings_tr[3]),7, na.rm=TRUE, fill=NA, align='right')

#print(rollingAvgSWE_GAP_tr)
#compute rolling sd global active power grouped by times of day
#rollingSdSWE_GAP_tr <- roll_sd(as.matrix(sdSWE_GAP_tr[2]), n = 7L, na.rm = TRUE)


#rollingAvgSWE_GAP_tr <- c(0,0,0,0,0,0,rollingAvgSWE_GAP_tr)

df1 <- data.frame(average = summeravgSWE_GAP_tr[2],Rolling_average = summerrollingAvgSWE_GAP_tr)
df2 <- data.frame(average = springavgSWE_GAP_tr[2],Rolling_average = springrollingAvgSWE_GAP_tr)
df3 <- data.frame(average = fallavgSWE_GAP_tr[2],Rolling_average = fallrollingAvgSWE_GAP_tr)
df4 <- data.frame(average = winteravgSWE_GAP_tr[2],Rolling_average = winterrollingAvgSWE_GAP_tr)
#df <- melt(df ,  id.vars = 'Time', variable.name = 'series')


#,sd=sdSWE_GAP_tr[2], Rsd_sd = rollingSdSWE_GAP_tr
#print(df)

plot.ts(df1, main = "Summer Wednesday nights (7-11pm) window average of size 7 vs true average")
plot.ts(df2, main = "Spring Wednesday nights (7-11pm) window average of size 7 vs true average")
plot.ts(df3, main = "Fall Wednesday nights (7-11pm) window average of size 7 vs true average")
plot.ts(df4, main = "Winter Wednesday nights (7-11pm) window average of size 7 vs true average")




#--------------------
