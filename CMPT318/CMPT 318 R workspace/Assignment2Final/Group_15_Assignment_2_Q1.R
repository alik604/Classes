library("depmixS4")
library(plyr)

getwd()
setwd("\Users\kali\Documents")

data <- read.table("Dataset3.txt",header = TRUE, sep = ",")
data$day <- as.POSIXlt(data$Date,format = "%d/%m/%Y")$wday

#Q1
sundayMornings <- data[data$day == 0 & strptime(data$Time,"%H:%M:%S") >= strptime("08:00:00","%H:%M:%S")
                       & strptime(data$Time,"%H:%M:%S") < strptime("11:00:00","%H:%M:%S"),]

sundayMornings <- sundayMornings[order(as.Date(sundayMornings$Date, format = "%d/%m/%Y"), strptime(sundayMornings$Time,"%H:%M:%S")),]

GAP_GRP_cor <- cor(sundayMornings$Global_active_power, sundayMornings$Global_reactive_power, method = "pearson")
GAP_Vol_cor <- cor(sundayMornings$Global_active_power, sundayMornings$Voltage, method = "pearson")
GAP_GI_cor <- cor(sundayMornings$Global_active_power, sundayMornings$Global_intensity, method = "pearson")

Nmin <- 12
Nmax <- 18
Nstates <- Nmin:Nmax
BIC <- vector("list", Nmax - Nmin + 1)
ct <- count(sundayMornings, "Date")
ct <- ct[order(as.Date(ct$Date, format = "%d/%m/%Y")),]

set.seed(2)
for (n in Nstates)
{
  mod <- depmix(response = Global_active_power ~ 1, data = sundayMornings, nstates = n, ntimes = ct$freq)
  
  fm <- fit(mod)
  summary(fm)

  BIC[[n-Nmin+1]] <- BIC(fm)
}

plot(Nstates, BIC, ty="b")


# mod1 <- depmix(response = Global_active_power ~ 1, data = sundayMornings, nstates = 2)
# 
# fm1 <- fit(mod1)
# 
# summary(fm1)
# print(fm1)
# 
# mod2 <- depmix(response = Global_active_power ~ 1, data = sundayMornings, nstates = 3)
# 
# fm2 <- fit(mod2)
# 
# summary(fm2)
# print(fm2)

#mod1 <- depmix(response = list(Global_active_power ~ 1, Global_intensity ~ 1), data = sundayMornings, nstates = 10, 
#               family = list(gaussian(), gaussian()))