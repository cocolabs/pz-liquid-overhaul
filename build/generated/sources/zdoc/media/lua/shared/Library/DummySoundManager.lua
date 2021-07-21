---@class DummySoundManager : zombie.DummySoundManager
---@field private ambientPieces ArrayList|Unknown
DummySoundManager = {}

---Specified by:
---
---Update in class BaseSoundManager
---@public
---@return void
function DummySoundManager:Update() end

---Specified by:
---
---update3 in class BaseSoundManager
---@public
---@return void
function DummySoundManager:update3() end

---@public
---@param arg0 String
---@param arg1 Audio
---@param arg2 float
---@param arg3 boolean
---@return void
---@overload fun(arg0:String, arg1:Audio, arg2:boolean, arg3:float)
function DummySoundManager:PlayAsMusic(arg0, arg1, arg2, arg3) end

---@public
---@param arg0 String
---@param arg1 Audio
---@param arg2 boolean
---@param arg3 float
---@return void
function DummySoundManager:PlayAsMusic(arg0, arg1, arg2, arg3) end

---Specified by:
---
---update2 in class BaseSoundManager
---@public
---@return void
function DummySoundManager:update2() end

---Specified by:
---
---Purge in class BaseSoundManager
---@public
---@return void
function DummySoundManager:Purge() end

---Specified by:
---
---stopMusic in class BaseSoundManager
---@public
---@param name String
---@return void
function DummySoundManager:stopMusic(name) end

---Specified by:
---
---IsMusicPlaying in class BaseSoundManager
---@public
---@return boolean
function DummySoundManager:IsMusicPlaying() end

---@public
---@param arg0 String
---@param arg1 boolean
---@param arg2 int
---@param arg3 int
---@param arg4 int
---@param arg5 float
---@param arg6 float
---@param arg7 float
---@param arg8 boolean
---@return Audio
function DummySoundManager:PlayWorldSoundImpl(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8) end

---Specified by:
---
---StopMusic in class BaseSoundManager
---@public
---@return void
function DummySoundManager:StopMusic() end

---@protected
---@param arg0 Audio
---@return boolean
function DummySoundManager:HasMusic(arg0) end

---@public
---@param arg0 Audio
---@param arg1 float
---@param arg2 String
---@return Audio
function DummySoundManager:Start(arg0, arg1, arg2) end

---@public
---@param arg0 float
---@return void
function DummySoundManager:setVehicleEngineVolume(arg0) end

---@public
---@param arg0 Audio
---@param arg1 float
---@param arg2 String
---@return Audio
function DummySoundManager:BlendThenStart(arg0, arg1, arg2) end

---@public
---@param arg0 BaseSoundEmitter
---@return void
function DummySoundManager:unregisterEmitter(arg0) end

---@public
---@return String
function DummySoundManager:getCurrentMusicName() end

---@public
---@param arg0 float
---@param arg1 float
---@param arg2 float
---@return boolean
function DummySoundManager:isListenerInRange(arg0, arg1, arg2) end

---Specified by:
---
---update4 in class BaseSoundManager
---@public
---@return void
function DummySoundManager:update4() end

---@public
---@param arg0 String
---@param arg1 String
---@param arg2 boolean
---@param arg3 float
---@return Audio
function DummySoundManager:PlayMusic(arg0, arg1, arg2, arg3) end

---@public
---@return float
function DummySoundManager:getAmbientVolume() end

---@public
---@return void
function DummySoundManager:resumeSoundAndMusic() end

---Specified by:
---
---CacheSound in class BaseSoundManager
---@public
---@param file String
---@return void
function DummySoundManager:CacheSound(file) end

---Specified by:
---
---FadeOutMusic in class BaseSoundManager
---@public
---@param name String
---@param milli int
---@return void
function DummySoundManager:FadeOutMusic(name, milli) end

---@public
---@param arg0 String
---@return Audio
function DummySoundManager:PrepareMusic(arg0) end

---Specified by:
---
---update3D in class BaseSoundManager
---@public
---@return void
function DummySoundManager:update3D() end

---@public
---@param arg0 String
---@param arg1 boolean
---@param arg2 float
---@return Audio
---@overload fun(arg0:String, arg1:boolean, arg2:float, arg3:float)
function DummySoundManager:PlaySound(arg0, arg1, arg2) end

---@public
---@param arg0 String
---@param arg1 boolean
---@param arg2 float
---@param arg3 float
---@return Audio
function DummySoundManager:PlaySound(arg0, arg1, arg2, arg3) end

---@public
---@return float
function DummySoundManager:getMusicVolume() end

---@public
---@param arg0 String
---@param arg1 boolean
---@param arg2 float
---@return Audio
function DummySoundManager:PlayJukeboxSound(arg0, arg1, arg2) end

---@public
---@return String
function DummySoundManager:getCurrentMusicLibrary() end

---@public
---@param arg0 String
---@param arg1 IsoGridSquare
---@param arg2 float
---@param arg3 float
---@param arg4 float
---@param arg5 boolean
---@return Audio
---@overload fun(name:String, source:IsoGridSquare, pitchVar:float, radius:float, maxGain:float, choices:int, ignoreOutside:boolean)
---@overload fun(arg0:String, arg1:boolean, arg2:IsoGridSquare, arg3:float, arg4:float, arg5:float, arg6:boolean)
function DummySoundManager:PlayWorldSoundWav(arg0, arg1, arg2, arg3, arg4, arg5) end

---Specified by:
---
---PlayWorldSoundWav in class BaseSoundManager
---@public
---@param name String
---@param source IsoGridSquare
---@param pitchVar float
---@param radius float
---@param maxGain float
---@param choices int
---@param ignoreOutside boolean
---@return void
function DummySoundManager:PlayWorldSoundWav(name, source, pitchVar, radius, maxGain, choices, ignoreOutside) end

---@public
---@param arg0 String
---@param arg1 boolean
---@param arg2 IsoGridSquare
---@param arg3 float
---@param arg4 float
---@param arg5 float
---@param arg6 boolean
---@return Audio
function DummySoundManager:PlayWorldSoundWav(arg0, arg1, arg2, arg3, arg4, arg5, arg6) end

---@public
---@return float
function DummySoundManager:getVehicleEngineVolume() end

---Specified by:
---
---setSoundVolume in class BaseSoundManager
---@public
---@param volume float
---@return void
function DummySoundManager:setSoundVolume(volume) end

---@public
---@param arg0 String
---@param arg1 boolean
---@param arg2 float
---@return Audio
---@overload fun(arg0:String, arg1:int, arg2:boolean, arg3:float)
---@overload fun(arg0:String, arg1:boolean, arg2:float, arg3:float)
function DummySoundManager:PlaySoundWav(arg0, arg1, arg2) end

---@public
---@param arg0 String
---@param arg1 int
---@param arg2 boolean
---@param arg3 float
---@return Audio
function DummySoundManager:PlaySoundWav(arg0, arg1, arg2, arg3) end

---@public
---@param arg0 String
---@param arg1 boolean
---@param arg2 float
---@param arg3 float
---@return Audio
function DummySoundManager:PlaySoundWav(arg0, arg1, arg2, arg3) end

---Specified by:
---
---playAmbient in class BaseSoundManager
---@public
---@param name String
---@return void
function DummySoundManager:playAmbient(name) end

---@public
---@param arg0 Audio
---@return void
function DummySoundManager:StopSound(arg0) end

---@public
---@return boolean
function DummySoundManager:isRemastered() end

---@public
---@param arg0 Audio
---@param arg1 float
---@return void
---@overload fun(arg0:Audio, arg1:float, arg2:float)
function DummySoundManager:BlendVolume(arg0, arg1) end

---@public
---@param arg0 Audio
---@param arg1 float
---@param arg2 float
---@return void
function DummySoundManager:BlendVolume(arg0, arg1, arg2) end

---@public
---@param arg0 BaseSoundEmitter
---@return void
function DummySoundManager:registerEmitter(arg0) end

---Specified by:
---
---getSoundVolume in class BaseSoundManager
---@public
---@return float
function DummySoundManager:getSoundVolume() end

---@public
---@param arg0 String
---@return void
function DummySoundManager:playNightAmbient(arg0) end

---@public
---@return ArrayList|Unknown
function DummySoundManager:getAmbientPieces() end

---@public
---@param arg0 String
---@param arg1 IsoGridSquare
---@param arg2 float
---@param arg3 float
---@param arg4 float
---@param arg5 boolean
---@return Audio
---@overload fun(arg0:String, arg1:IsoGridSquare, arg2:float, arg3:float, arg4:float, arg5:int, arg6:boolean)
---@overload fun(arg0:String, arg1:boolean, arg2:IsoGridSquare, arg3:float, arg4:float, arg5:float, arg6:boolean)
function DummySoundManager:PlayWorldSound(arg0, arg1, arg2, arg3, arg4, arg5) end

---@public
---@param arg0 String
---@param arg1 IsoGridSquare
---@param arg2 float
---@param arg3 float
---@param arg4 float
---@param arg5 int
---@param arg6 boolean
---@return Audio
function DummySoundManager:PlayWorldSound(arg0, arg1, arg2, arg3, arg4, arg5, arg6) end

---@public
---@param arg0 String
---@param arg1 boolean
---@param arg2 IsoGridSquare
---@param arg3 float
---@param arg4 float
---@param arg5 float
---@param arg6 boolean
---@return Audio
function DummySoundManager:PlayWorldSound(arg0, arg1, arg2, arg3, arg4, arg5, arg6) end

---@public
---@param arg0 float
---@return void
function DummySoundManager:setAmbientVolume(arg0) end

---Specified by:
---
---getMusicPosition in class BaseSoundManager
---@public
---@return float
function DummySoundManager:getMusicPosition() end

---@public
---@return void
function DummySoundManager:debugScriptSounds() end

---Specified by:
---
---DoMusic in class BaseSoundManager
---@public
---@param name String
---@param bLoop boolean
---@return void
function DummySoundManager:DoMusic(name, bLoop) end

---@public
---@param arg0 String
---@param arg1 boolean
---@param arg2 IsoGridSquare
---@param arg3 float
---@param arg4 float
---@param arg5 float
---@param arg6 boolean
---@return Audio
function DummySoundManager:PlayWorldSoundWavImpl(arg0, arg1, arg2, arg3, arg4, arg5, arg6) end

---@public
---@return void
function DummySoundManager:stop() end

---Specified by:
---
---CheckDoMusic in class BaseSoundManager
---@public
---@return void
function DummySoundManager:CheckDoMusic() end

---@public
---@return void
function DummySoundManager:pauseSoundAndMusic() end

---Specified by:
---
---isPlayingMusic in class BaseSoundManager
---@public
---@return boolean
function DummySoundManager:isPlayingMusic() end

---Specified by:
---
---update1 in class BaseSoundManager
---@public
---@return void
function DummySoundManager:update1() end

---@public
---@param arg0 String
---@param arg1 boolean
---@param arg2 float
---@return Audio
function DummySoundManager:PlaySoundEvenSilent(arg0, arg1, arg2) end

---Specified by:
---
---setMusicVolume in class BaseSoundManager
---@public
---@param volume float
---@return void
function DummySoundManager:setMusicVolume(volume) end

---Specified by:
---
---playMusic in class BaseSoundManager
---@public
---@param name String
---@return void
function DummySoundManager:playMusic(name) end

---Specified by:
---
---playMusicNonTriggered in class BaseSoundManager
---@public
---@param name String
---@param gain float
---@return void
function DummySoundManager:playMusicNonTriggered(name, gain) end
