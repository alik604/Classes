
getwd()
setwd("\Users\kali\Documents")
data <- read.table("Dataset3.txt",header = TRUE, sep = ",")
data$day <- as.POSIXlt(data$Date,format = "%d/%m/%Y")$wday

statsmode <- function(x)
{
  u <- unique(x)
  u[which.max(tabulate(match(x,u)))]
}

#Q1
A <- data$Global_active_power
Amean <- mean(A,na.rm = TRUE)
Agmean <- exp(mean(log(A),na.rm = TRUE))
Amedian <- median(A,na.rm = TRUE)
Amode <- statsmode(A)
Asd <- sqrt(var(A,na.rm = TRUE))

B <- data$Global_reactive_power
Bmean <- mean(B,na.rm = TRUE)
Bgmean <- exp(mean(log(B),na.rm = TRUE))
Bmedian <- median(B,na.rm = TRUE)
Bmode <- statsmode(B)
Bsd <- sqrt(var(B,na.rm = TRUE))

print(paste("Arithmetic mean active power: ", Amean))
print(paste("Geometric mean active power: ", Agmean))
print(paste("Median active power: ", Amedian))
print(paste("Mode active power: ", Amode))
print(paste("Standard dev active power:", Asd))
print('')
print(paste("Arithmetic mean reactive power: ", Bmean))
print(paste("Geometric mean reactive power: ", Bgmean))
print(paste("Median reactive power: ", Bmedian))
print(paste("Mode reactive power: ", Bmode))
print(paste("Standard dev reactive power:", Bsd))

#Q2
C <- data$Voltage
D <- data$Global_intensity

cor_active_reactive <- cor(A, B, use = "na.or.complete", method="pearson")
cor_active_voltage <- cor(A, C, use = "na.or.complete", method="pearson")
cor_active_intensity <- cor(A, D, use = "na.or.complete", method="pearson")
cor_reactive_voltage <- cor(B, C, use = "na.or.complete", method="pearson")
cor_reactive_intensity <- cor(B, D, use = "na.or.complete", method="pearson")
cor_intensity_voltage <- cor(C, D, use = "na.or.complete", method="pearson")

print('')
print(paste("Correlation active power and reactive power: ", cor_active_reactive))
print(paste("Correlation active power and voltage: ", cor_active_voltage))
print(paste("Correlation active power and intensity: ", cor_active_intensity))
print(paste("Correlation reactive power and voltage: ", cor_reactive_voltage))
print(paste("Correlation reactive power and intensity: ", cor_reactive_intensity))
print(paste("Correlation voltage and intensity: ", cor_intensity_voltage))



#Q3

weekendData <- data[data$day == 0 | data$day == 6,]
weekdayData <- data[data$day > 0 & data$day < 6,]

Aweekend <- weekendData$Global_active_power
Aweekday <- weekdayData$Global_active_power

AminWeekend <- min(Aweekend,na.rm = TRUE)
AmaxWeekend <- max(Aweekend,na.rm = TRUE)

AminWeekday <- min(Aweekday,na.rm = TRUE)
AmaxWeekday <- max(Aweekday,na.rm = TRUE)

Bweekend <- weekendData$Global_reactive_power
Bweekday <- weekdayData$Global_reactive_power

BminWeekend <- min(Bweekend,na.rm = TRUE)
BmaxWeekend <- max(Bweekend,na.rm = TRUE)

BminWeekday <- min(Bweekday,na.rm = TRUE)
BmaxWeekday <- max(Bweekday,na.rm = TRUE)

print('')
print(paste("Maximum active power for weekdays is: ", AmaxWeekday))
print(paste("Maximum active power for weekends is: ", AmaxWeekend))
print(paste("Maximum reactive power for weekdays is: ", BmaxWeekday))
print(paste("Maximum reactive power for weekends is: ", BmaxWeekend))
print("")
print(paste("Min active power for weekdays is: ", AminWeekday))
print(paste("Min active power for weekends is: ", AminWeekend))
print(paste("Min reactive power for weekdays is: ", BminWeekday))
print(paste("Min reactive power for weekends is: ", BminWeekend))