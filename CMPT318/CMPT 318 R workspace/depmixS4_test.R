library("depmixS4")
data("speed")
set.seed(1)

head(speed)
?speed

mod1 <- depmix(response = rt ~ 1, data = speed, nstates = 2)
fm1 <- fit(mod1)

summary(fm1)
print(fm1)

###############################################

mod2 <- depmix(response = rt ~ 1, data = speed, nstates = 3)
fm2 <- fit(mod2)

summary(fm2)
print(fm2)

###############################################

mod3 <- depmix(response = rt ~ 1, data = speed, nstates = 4)
fm3 <- fit(mod3)

summary(fm3)
print(fm3)

###############################################

mod4 <- depmix(response = rt ~ 1, data = speed, nstates = 5)
fm4 <- fit(mod4)

summary(fm4)
print(fm4)

###############################################

plot(1:4,c(BIC(fm1),BIC(fm2),BIC(fm3),BIC(fm4)),ty="b")

###############################################

mod3 <- depmix(response = corr ~ 1, data = speed, nstates = 2, family = multinomial())
fm3 <- fit(mod3)

summary(fm3)

###############################################

modmulti <- depmix(list(rt~1,corr~1),data=speed,nstates=2,family=list(gaussian(),multinomial()))

fmodmulti <- fit(modmulti)

print(fmodmulti)
summary(fmodmulti)

###############################################

mod4 <- depmix(response = rt ~ 1, data = speed, nstates = 2,
               trstart = runif(4), instart = c(0.71, 0.28))

fm4 <- fit(mod4)

summary(fm4)
print(fm4)

###############################################
mod_ntimes <- depmix(list(rt~1,corr~1), data=speed, nstates=2,
              family=list(gaussian(),multinomial("identity")), ntimes=c(168,134,137))

fm_ntimes <- fit(mod_ntimes)