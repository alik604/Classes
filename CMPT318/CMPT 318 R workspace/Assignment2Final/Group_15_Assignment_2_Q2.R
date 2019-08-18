library("depmixS4")
library(parallel)
library(plyr)

getwd()
setwd("\Users\kali\Documents")

data <- read.table("Dataset3.txt",header = TRUE, sep = ",")
data$day <- as.POSIXlt(data$Date,format = "%d/%m/%Y")$wday

#Q2
sundayNights <- data[data$day == 0 & strptime(data$Time,"%H:%M:%S") >= strptime("21:00:00","%H:%M:%S")
                     & strptime(data$Time,"%H:%M:%S") < strptime("24:00:00","%H:%M:%S"),]

sundayNights <- sundayNights[order(as.Date(sundayNights$Date, format = "%d/%m/%Y"), strptime(sundayNights$Time,"%H:%M:%S")),]

GAP_GRP_cor <- cor(sundayNights$Global_active_power, sundayNights$Global_reactive_power, method = "pearson")
GAP_Vol_cor <- cor(sundayNights$Global_active_power, sundayNights$Voltage, method = "pearson")
GAP_GI_cor <- cor(sundayNights$Global_active_power, sundayNights$Global_intensity, method = "pearson")

Nmin <- 2
Nmax <- 20
Nstates <- Nmin:Nmax
BIC <- vector("list", Nmax - Nmin + 1)
ct <- count(sundayNights, "Date")
ct <- ct[order(as.Date(ct$Date, format = "%d/%m/%Y")),]

set.seed(2)
for (n in Nstates)
{
  mod <- depmix(response = Global_active_power ~ 1, data = sundayNights, nstates = n, ntimes = ct$freq)
  
  fm <- fit(mod)
  
  BIC[[n-Nmin+1]] <- BIC(fm)
}

plot(Nstates, BIC, ty="b")
