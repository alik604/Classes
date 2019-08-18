library("depmixS4")
library(ggplot2)

# getwd()
setwd("/Users/Andrei/Desktop/cmpt318/")

#https://stackoverflow.com/questions/24387376/r-error-could-not-find-function-multiplot-using-cookbook-example
# Multiple plot function
#
# ggplot objects can be passed in ..., or to plotlist (as a list of ggplot objects)
# - cols:   Number of columns in layout
# - layout: A matrix specifying the layout. If present, 'cols' is ignored.
#
# If the layout is something like matrix(c(1,2,3,3), nrow=2, byrow=TRUE),
# then plot 1 will go in the upper left, 2 will go in the upper right, and
# 3 will go all the way across the bottom.
#
multiplot <- function(..., plotlist=NULL, file, cols=1, layout=NULL) {
  require(grid)
  
  # Make a list from the ... arguments and plotlist
  plots <- c(list(...), plotlist)
  
  numPlots = length(plots)
  
  # If layout is NULL, then use 'cols' to determine layout
  if (is.null(layout)) {
    # Make the panel
    # ncol: Number of columns of plots
    # nrow: Number of rows needed, calculated from # of cols
    layout <- matrix(seq(1, cols * ceiling(numPlots/cols)),
                     ncol = cols, nrow = ceiling(numPlots/cols))
  }
  
  if (numPlots==1) {
    print(plots[[1]])
    
  } else {
    # Set up the page
    grid.newpage()
    pushViewport(viewport(layout = grid.layout(nrow(layout), ncol(layout))))
    
    # Make each plot, in the correct location
    for (i in 1:numPlots) {
      # Get the i,j matrix positions of the regions that contain this subplot
      matchidx <- as.data.frame(which(layout == i, arr.ind = TRUE))
      
      print(plots[[i]], vp = viewport(layout.pos.row = matchidx$row,
                                      layout.pos.col = matchidx$col))
    }
  }
}

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

data <- read.table("Train Data.txt",header = TRUE, sep = ",")

data$Date <- as.POSIXct(data$Date, format="%d/%m/%Y")
data$Date <- format(data$Date, format("%m/%d"))

activePower_aggregateDF <- aggregate(data$Global_active_power, list(Date=data$Date, Time=data$Time), mean)
intensity_aggregateDF <- aggregate(data$Global_intensity, list(Date=data$Date, Time=data$Time), mean)
colnames(activePower_aggregateDF)[3] <- "Global_active_power"
colnames(intensity_aggregateDF)[3] <- "Global_intensity"
aggregateDF <- merge(activePower_aggregateDF, intensity_aggregateDF, by=c("Date", "Time"))
colnames(aggregateDF)[3] <- "Global_active_power"
colnames(aggregateDF)[4] <- "Global_intensity"

aggregateDF$Date <- as.POSIXct(aggregateDF$Date, format="%m/%d")
aggregateDF$season <- getSeason(aggregateDF$Date)
aggregateDF$day <- as.POSIXlt(aggregateDF$Date,format = "%d/%m/%Y")$wday

earlyMorningStart <- strptime("19:00:00", format="%H:%M:%S") 
earlyMorningEnd <- strptime("20:00:00", format="%H:%M:%S")
season <- 3
xday <- 3

filterDF <- split(aggregateDF, 
                  (strptime(aggregateDF$Time, format="%H:%M:%S") >= earlyMorningStart) &
                    (strptime(aggregateDF$Time, format="%H:%M:%S") < earlyMorningEnd) &
                    (aggregateDF$season == season) &
                    (aggregateDF$day == xday),
                  drop = TRUE)

# This doesn't change 
earlyMornSpringDF <- data.frame(Global_active_power = filterDF[[2]][[3]],
                                Global_intensity = filterDF[[2]][[4]])

earlyMornSpringLM <- lm(Global_intensity ~ Global_active_power, data=earlyMornSpringDF)
summary(earlyMornSpringLM)

### TEST 1 ### 

test1DF <- read.table("test1.txt",header = TRUE, sep = ",")

test1DF$Date <- as.POSIXct(test1DF$Date, format="%d/%m/%Y")
test1DF$Date <- format(test1DF$Date, format("%m/%d"))
test1DF$Date <- as.POSIXct(test1DF$Date, format="%m/%d")
test1DF$season <- getSeason(test1DF$Date)
test1DF$day <- as.POSIXlt(test1DF$Date,format = "%d/%m/%Y")$wday

filterDF <- split(test1DF, 
                  (strptime(test1DF$Time, format="%H:%M:%S") >= earlyMorningStart) &
                    (strptime(test1DF$Time, format="%H:%M:%S") < earlyMorningEnd) &
                    (test1DF$season == season) &
                    (test1DF$day == xday),
                  drop = TRUE)
# filterDF[[2]][[x]] represents the column number of the datase
test1DF <- data.frame(Global_active_power = filterDF[[2]][[3]],
                      Global_intensity = filterDF[[2]][[6]])

test1_earlyMornSpringPrediction <- predict(earlyMornSpringLM, test1DF)
#test1_actual_earlyMornSpringPrediction <- data.frame(cbind(actuals=test1DF, predicteds=earlyMornSpringPrediction))

#test1_correlation_accuracy <- cor(actual_earlyMornSpringPrediction$Global_intensity, actual_earlyMornSpringPrediction$predicteds, use="complete.obs")
#head(actual_earlyMornSpringPrediction)

### TEST 2 ### 

test2DF <- read.table("test2.txt",header = TRUE, sep = ",")

test2DF$Date <- as.POSIXct(test2DF$Date, format="%d/%m/%Y")
test2DF$Date <- format(test2DF$Date, format("%m/%d"))
test2DF$Date <- as.POSIXct(test2DF$Date, format="%m/%d")
test2DF$season <- getSeason(test2DF$Date)
test2DF$day <- as.POSIXlt(test2DF$Date,format = "%d/%m/%Y")$wday

filterDF <- split(test2DF, 
                  (strptime(test2DF$Time, format="%H:%M:%S") >= earlyMorningStart) &
                    (strptime(test2DF$Time, format="%H:%M:%S") < earlyMorningEnd) &
                    (test2DF$season == season) &
                    (test2DF$day == xday),
                  drop = TRUE)
# filterDF[[2]][[x]] represents the column number of the datase
test2DF <- data.frame(Global_active_power = filterDF[[2]][[3]],
                      Global_intensity = filterDF[[2]][[6]])

test2_earlyMornSpringPrediction <- predict(earlyMornSpringLM, test2DF)

### TEST 3 ### 

test3DF <- read.table("test3.txt",header = TRUE, sep = ",")

test3DF$Date <- as.POSIXct(test3DF$Date, format="%d/%m/%Y")
test3DF$Date <- format(test3DF$Date, format("%m/%d"))
test3DF$Date <- as.POSIXct(test3DF$Date, format="%m/%d")
test3DF$season <- getSeason(test3DF$Date)
test3DF$day <- as.POSIXlt(test3DF$Date,format = "%d/%m/%Y")$wday

filterDF <- split(test3DF, 
                  (strptime(test3DF$Time, format="%H:%M:%S") >= earlyMorningStart) &
                    (strptime(test3DF$Time, format="%H:%M:%S") < earlyMorningEnd) &
                    (test3DF$season == season) &
                    (test3DF$day == xday),
                  drop = TRUE)
# filterDF[[2]][[x]] represents the column number of the datase
test3DF <- data.frame(Global_active_power = filterDF[[2]][[3]],
                      Global_intensity = filterDF[[2]][[6]])

test3_earlyMornSpringPrediction <- predict(earlyMornSpringLM, test3DF)

### TEST 4 ### 

test4DF <- read.table("test4.txt",header = TRUE, sep = ",")

test4DF$Date <- as.POSIXct(test4DF$Date, format="%d/%m/%Y")
test4DF$Date <- format(test4DF$Date, format("%m/%d"))
test4DF$Date <- as.POSIXct(test4DF$Date, format="%m/%d")
test4DF$season <- getSeason(test4DF$Date)
test4DF$day <- as.POSIXlt(test4DF$Date,format = "%d/%m/%Y")$wday

filterDF <- split(test4DF, 
                  (strptime(test4DF$Time, format="%H:%M:%S") >= earlyMorningStart) &
                    (strptime(test4DF$Time, format="%H:%M:%S") < earlyMorningEnd) &
                    (test4DF$season == season) &
                    (test4DF$day == xday),
                  drop = TRUE)
# filterDF[[2]][[x]] represents the column number of the datase
test4DF <- data.frame(Global_active_power = filterDF[[2]][[3]],
                      Global_intensity = filterDF[[2]][[6]])

test4_earlyMornSpringPrediction <- predict(earlyMornSpringLM, test4DF)

### TEST 5 ### 

test5DF <- read.table("test5.txt",header = TRUE, sep = ",")

test5DF$Date <- as.POSIXct(test5DF$Date, format="%d/%m/%Y")
test5DF$Date <- format(test5DF$Date, format("%m/%d"))
test5DF$Date <- as.POSIXct(test5DF$Date, format="%m/%d")
test5DF$season <- getSeason(test5DF$Date)
test5DF$day <- as.POSIXlt(test5DF$Date,format = "%d/%m/%Y")$wday

filterDF <- split(test5DF, 
                  (strptime(test5DF$Time, format="%H:%M:%S") >= earlyMorningStart) &
                    (strptime(test5DF$Time, format="%H:%M:%S") < earlyMorningEnd) &
                    (test5DF$season == season) &
                    (test5DF$day == xday),
                  drop = TRUE)
# filterDF[[2]][[x]] represents the column number of the datase
test5DF <- data.frame(Global_active_power = filterDF[[2]][[3]],
                      Global_intensity = filterDF[[2]][[6]])

test5_earlyMornSpringPrediction <- predict(earlyMornSpringLM, test5DF)

### SUMMARY ### 

actual_earlyMornSpringPrediction <- data.frame(cbind(test1=test1DF$Global_intensity, 
                                                     test2=test2DF$Global_intensity, 
                                                     test3=test3DF$Global_intensity, 
                                                     test4=test4DF$Global_intensity, 
                                                     test5=test5DF$Global_intensity, 
                                                     predicteds=test1_earlyMornSpringPrediction))

# First Plot
p1 <- ggplot(actual_earlyMornSpringPrediction, aes(as.numeric(row.names(actual_earlyMornSpringPrediction))), geom = "point") + 
  geom_point(aes(y = predicteds, colour = "Predictions")) + 
  geom_point(aes(y = test1, colour = "Test1")) +
  labs(title = "Predicted power vs Test1", x="Observations")

# Second Plot
p2 <- ggplot(actual_earlyMornSpringPrediction, aes(as.numeric(row.names(actual_earlyMornSpringPrediction))), geom = "point") + 
  geom_point(aes(y = predicteds, colour = "Predictions")) + 
  geom_point(aes(y = test2, colour = "Test2")) +
  labs(title = "Predicted power vs Test2", x="Observations")

# Third Plot
p3 <- ggplot(actual_earlyMornSpringPrediction, aes(as.numeric(row.names(actual_earlyMornSpringPrediction))), geom = "point") + 
  geom_point(aes(y = predicteds, colour = "Predictions")) + 
  geom_point(aes(y = test3, colour = "Test3")) +
  labs(title = "Predicted power vs Test3", x="Observations")

# Forth Plot
p4 <- ggplot(actual_earlyMornSpringPrediction, aes(as.numeric(row.names(actual_earlyMornSpringPrediction))), geom = "point") + 
  geom_point(aes(y = predicteds, colour = "Predictions")) + 
  geom_point(aes(y = test4, colour = "Test4")) +
  labs(title = "Predicted power vs Test4", x="Observations")

# Fifth Plot
p5 <- ggplot(actual_earlyMornSpringPrediction, aes(as.numeric(row.names(actual_earlyMornSpringPrediction))), geom = "point") + 
  geom_point(aes(y = predicteds, colour = "Predictions")) + 
  geom_point(aes(y = test5, colour = "Test5")) +
  labs(title = "Predicted power vs Test5", x="Observations")

multiplot(p1, p2, p3, p4, p5, cols=1)
#, aes(date)) + 
#  geom_line(aes(y = var0, colour = "var0")) + 
  #geom_line(aes(y = var1, colour = "var1"))