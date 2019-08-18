library(ggplot2)
library("depmixS4")
library(plyr)

getwd()
setwd("C:/Users/Stefano/Documents/SFU/CMPT318/Project")

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

test1 <- read.table("test1.txt",header = TRUE, sep = ",")
test1$day <- as.POSIXlt(test1$Date,format = "%d/%m/%Y")$wday
test1$month <- format(as.Date(test1$Date), "%m")
test1$season <- getSeason(test1$Date)

test2 <- read.table("test2.txt",header = TRUE, sep = ",")
test2$day <- as.POSIXlt(test2$Date,format = "%d/%m/%Y")$wday
test2$month <- format(as.Date(test2$Date), "%m")
test2$season <- getSeason(test2$Date)

test3 <- read.table("test3.txt",header = TRUE, sep = ",")
test3$day <- as.POSIXlt(test3$Date,format = "%d/%m/%Y")$wday
test3$month <- format(as.Date(test3$Date), "%m")
test3$season <- getSeason(test3$Date)

test4 <- read.table("test4.txt",header = TRUE, sep = ",")
test4$day <- as.POSIXlt(test4$Date,format = "%d/%m/%Y")$wday
test4$month <- format(as.Date(test4$Date), "%m")
test4$season <- getSeason(test4$Date)

test5 <- read.table("test5.txt",header = TRUE, sep = ",")
test5$day <- as.POSIXlt(test5$Date,format = "%d/%m/%Y")$wday
test5$month <- format(as.Date(test5$Date), "%m")
test5$season <- getSeason(test5$Date)

#data partitioning in to wednesday evenings in the summer
xseason <- 2
xday <- 3
timelower <- "19:00:00"
timeupper <- "23:00:00"

summerWednesdayEvenings_tr <- train[train$season == xseason & train$day == xday & strptime(train$Time,"%H:%M:%S") >= strptime(timelower,"%H:%M:%S")
                                    & strptime(train$Time,"%H:%M:%S") < strptime(timeupper,"%H:%M:%S"),]

summerWednesdayEvenings_te1 <- test1[test1$season == xseason & test1$day == xday & strptime(test1$Time,"%H:%M:%S") >= strptime(timelower,"%H:%M:%S")
                                     & strptime(test1$Time,"%H:%M:%S") < strptime(timeupper,"%H:%M:%S"),]

summerWednesdayEvenings_te2 <- test2[test2$season == xseason & test2$day == xday & strptime(test2$Time,"%H:%M:%S") >= strptime(timelower,"%H:%M:%S")
                                     & strptime(test2$Time,"%H:%M:%S") < strptime(timeupper,"%H:%M:%S"),]

summerWednesdayEvenings_te3 <- test3[test3$season == xseason & test3$day == xday & strptime(test3$Time,"%H:%M:%S") >= strptime(timelower,"%H:%M:%S")
                                     & strptime(test3$Time,"%H:%M:%S") < strptime(timeupper,"%H:%M:%S"),]

summerWednesdayEvenings_te4 <- test4[test4$season == xseason & test4$day == xday & strptime(test4$Time,"%H:%M:%S") >= strptime(timelower,"%H:%M:%S")
                                     & strptime(test4$Time,"%H:%M:%S") < strptime(timeupper,"%H:%M:%S"),]

summerWednesdayEvenings_te5 <- test5[test5$season == xseason & test5$day == xday & strptime(test5$Time,"%H:%M:%S") >= strptime(timelower,"%H:%M:%S")
                                     & strptime(test5$Time,"%H:%M:%S") < strptime(timeupper,"%H:%M:%S"),]

Nmin <- 10
Nmax <- 20
Nstates <- Nmin:Nmax
BIC <- vector("list", Nmax - Nmin + 1)
ct <- count(summerWednesdayEvenings_tr, "Date")
ct <- ct[order(as.Date(ct$Date, format = "%d/%m/%Y")),]

#loop to find number of states the give lowest BIC
# set.seed(2)
# for (n in Nstates)
# {
#   mod <- depmix(response = Global_active_power ~ 1, data = summerWednesdayEvenings_tr, nstates = n, ntimes = ct$freq)
#   
#   fm <- fit(mod)
#   
#   BIC[[n-Nmin+1]] <- BIC(fm)
# }
# 
# plot(Nstates, BIC, ty="b")
# 
# print(BIC)


#best model
n_best = 19
mod <- depmix(response = Global_active_power ~ 1, data = summerWednesdayEvenings_tr, nstates = n_best, ntimes = ct$freq)
fm <- fit(mod)

ct_te1 <- count(summerWednesdayEvenings_te1, "Date")
ct_te1 <- ct_te1[order(as.Date(ct_te1$Date, format = "%d/%m/%Y")),]
init_mod_te1 <- depmix(response = Global_active_power ~ 1, data = summerWednesdayEvenings_te1, nstates = n_best, ntimes = ct_te1$freq)
mod_te1 <- setpars(init_mod_te1, getpars(fm))
fb_te1 <- forwardbackward(mod_te1)

ct_te2 <- count(summerWednesdayEvenings_te1, "Date")
ct_te2 <- ct_te2[order(as.Date(ct_te2$Date, format = "%d/%m/%Y")),]
init_mod_te2 <- depmix(response = Global_active_power ~ 1, data = summerWednesdayEvenings_te2, nstates = n_best, ntimes = ct_te2$freq)
mod_te2 <- setpars(init_mod_te2, getpars(fm))
fb_te2 <- forwardbackward(mod_te2)

ct_te3 <- count(summerWednesdayEvenings_te1, "Date")
ct_te3 <- ct_te3[order(as.Date(ct_te3$Date, format = "%d/%m/%Y")),]
init_mod_te3 <- depmix(response = Global_active_power ~ 1, data = summerWednesdayEvenings_te3, nstates = n_best, ntimes = ct_te3$freq)
mod_te3 <- setpars(init_mod_te3, getpars(fm))
fb_te3 <- forwardbackward(mod_te3)

ct_te4 <- count(summerWednesdayEvenings_te1, "Date")
ct_te4 <- ct_te4[order(as.Date(ct_te4$Date, format = "%d/%m/%Y")),]
init_mod_te4 <- depmix(response = Global_active_power ~ 1, data = summerWednesdayEvenings_te4, nstates = n_best, ntimes = ct_te4$freq)
mod_te4 <- setpars(init_mod_te4, getpars(fm))
fb_te4 <- forwardbackward(mod_te4)

ct_te5 <- count(summerWednesdayEvenings_te1, "Date")
ct_te5 <- ct_te5[order(as.Date(ct_te5$Date, format = "%d/%m/%Y")),]
init_mod_te5 <- depmix(response = Global_active_power ~ 1, data = summerWednesdayEvenings_te5, nstates = n_best, ntimes = ct_te5$freq)
mod_te5 <- setpars(init_mod_te5, getpars(fm))
fb_te5 <- forwardbackward(mod_te5)

print(fb_te1[6])
print(fb_te2[6])
print(fb_te3[6])
print(fb_te4[6])
print(fb_te5[6])
print(fm)