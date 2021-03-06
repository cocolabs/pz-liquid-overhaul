---@class DeviceData : zombie.radio.devices.DeviceData
---@field private deviceSpeakerSoundMod float
---@field private deviceButtonSoundVol float
---@field protected deviceName String
---@field protected twoWay boolean
---@field protected transmitRange int
---@field protected micRange int
---@field protected micIsMuted boolean
---@field protected baseVolumeRange float
---@field protected deviceVolume float
---@field protected isPortable boolean
---@field protected isTelevision boolean
---@field protected isHighTier boolean
---@field protected isTurnedOn boolean
---@field protected channel int
---@field protected minChannelRange int
---@field protected maxChannelRange int
---@field protected presets DevicePresets
---@field protected isBatteryPowered boolean
---@field protected hasBattery boolean
---@field protected powerDelta float
---@field protected useDelta float
---@field protected lastRecordedDistance int
---@field protected headphoneType int
---@field protected parent WaveSignalDevice
---@field protected gameTime GameTime
---@field protected channelChangedRecently boolean
---@field protected emitter BaseSoundEmitter
---@field protected soundIDs ArrayList|Unknown
---@field private soundCounterStatic float
---@field protected radioLoopSound long
---@field protected doTriggerWorldSound boolean
---@field protected lastMinuteStamp long
---@field protected listenCnt int
---@field nextStaticSound float
---@field protected signalCounter float
---@field protected soundCounter float
DeviceData = {}

---@public
---@return boolean
function DeviceData:isVehicleDevice() end

---@public
---@param arg0 float
---@return void
function DeviceData:setPower(arg0) end

---@public
---@param arg0 int
---@return void
---@overload fun(arg0:int, arg1:boolean)
function DeviceData:setChannel(arg0) end

---@public
---@param arg0 int
---@param arg1 boolean
---@return void
function DeviceData:setChannel(arg0, arg1) end

---@public
---@param arg0 float
---@return void
function DeviceData:setDeviceVolumeRaw(arg0) end

---@public
---@return float
function DeviceData:getPower() end

---@public
---@param arg0 boolean
---@return void
function DeviceData:setIsTwoWay(arg0) end

---@public
---@param arg0 boolean
---@return void
function DeviceData:setIsHighTier(arg0) end

---@public
---@param arg0 DrainableComboItem
---@return void
function DeviceData:addBattery(arg0) end

---@public
---@param arg0 String
---@param arg1 boolean
---@return void
function DeviceData:playSoundLocal(arg0, arg1) end

---@private
---@param arg0 UdpConnection
---@param arg1 byte
---@return void
function DeviceData:sendDeviceDataStatePacket(arg0, arg1) end

---@public
---@param arg0 int
---@return void
function DeviceData:doReceiveSignal(arg0) end

---@public
---@return void
function DeviceData:transmitPresets() end

---@public
---@return boolean
function DeviceData:getIsTwoWay() end

---@public
---@param arg0 boolean
---@return void
function DeviceData:setHasBattery(arg0) end

---@public
---@param arg0 int
---@return void
function DeviceData:setChannelRaw(arg0) end

---@public
---@return boolean
function DeviceData:isIsoDevice() end

---@private
---@return void
function DeviceData:setNextStaticSound() end

---@public
---@return boolean
function DeviceData:isInventoryDevice() end

---@protected
---@return void
function DeviceData:updateEmitter() end

---@public
---@return boolean
function DeviceData:canBePoweredHere() end

---@public
---@param arg0 int
---@return void
function DeviceData:setMinChannelRange(arg0) end

---@public
---@param arg0 boolean
---@return void
function DeviceData:setMicIsMuted(arg0) end

---@public
---@return void
function DeviceData:setRandomChannel() end

---@public
---@return float
function DeviceData:getBaseVolumeRange() end

---@public
---@return int
function DeviceData:getDeviceSoundVolumeRange() end

---@public
---@return float
function DeviceData:getUseDelta() end

---@public
---@return boolean
function DeviceData:getIsHighTier() end

---@private
---@param arg0 byte
---@return void
function DeviceData:transmitDeviceDataState(arg0) end

---@public
---@return int
function DeviceData:getLastRecordedDistance() end

---@public
---@return int
function DeviceData:getMaxChannelRange() end

---@public
---@param arg0 ByteBuffer
---@param arg1 int
---@param arg2 boolean
---@return void
function DeviceData:load(arg0, arg1, arg2) end

---@public
---@param arg0 DevicePresets
---@return void
function DeviceData:setDevicePresets(arg0) end

---@public
---@return boolean
function DeviceData:getMicIsMuted() end

---@private
---@param arg0 byte
---@param arg1 UdpConnection
---@return void
function DeviceData:transmitDeviceDataStateServer(arg0, arg1) end

---@public
---@return DevicePresets
function DeviceData:getDevicePresets() end

---@public
---@return int
function DeviceData:getMicRange() end

---@public
---@return WaveSignalDevice
function DeviceData:getParent() end

---@public
---@return void
function DeviceData:transmitBattryChange() end

---@public
---@param arg0 boolean
---@return void
function DeviceData:setTurnedOnRaw(arg0) end

---@public
---@param arg0 boolean
---@return void
function DeviceData:setIsTurnedOn(arg0) end

---@public
---@param arg0 String
---@return void
function DeviceData:setDeviceName(arg0) end

---@public
---@param arg0 boolean
---@return void
function DeviceData:TriggerPlayerListening(arg0) end

---@public
---@return boolean
function DeviceData:isReceivingSignal() end

---@public
---@return String
function DeviceData:getDeviceName() end

---@public
---@param arg0 ItemContainer
---@return InventoryItem
function DeviceData:getBattery(arg0) end

---@public
---@return void
function DeviceData:cleanSoundsAndEmitter() end

---@public
---@return boolean
function DeviceData:getIsTurnedOn() end

---@public
---@return void
function DeviceData:generatePresets() end

---@public
---@param arg0 InventoryItem
---@return void
function DeviceData:addHeadphones(arg0) end

---@public
---@return DeviceData
function DeviceData:getClone() end

---@public
---@param arg0 boolean
---@return void
function DeviceData:setIsBatteryPowered(arg0) end

---@public
---@return boolean
function DeviceData:getHasBattery() end

---@public
---@return int
function DeviceData:getMinChannelRange() end

---@private
---@return void
function DeviceData:updateStaticSounds() end

---@public
---@return boolean
function DeviceData:getIsPortable() end

---@public
---@param arg0 String
---@param arg1 float
---@param arg2 boolean
---@return void
function DeviceData:playSound(arg0, arg1, arg2) end

---@public
---@return int
function DeviceData:getDeviceVolumeRange() end

---@public
---@param arg0 int
---@return void
function DeviceData:setMaxChannelRange(arg0) end

---@public
---@param arg0 float
---@return void
function DeviceData:setBaseVolumeRange(arg0) end

---@public
---@return boolean
function DeviceData:getIsTelevision() end

---@public
---@param arg0 ByteBuffer
---@param arg1 UdpConnection
---@return void
function DeviceData:receiveDeviceDataStatePacket(arg0, arg1) end

---@public
---@param arg0 int
---@return void
function DeviceData:setMicRange(arg0) end

---@public
---@param arg0 boolean
---@return void
function DeviceData:setIsPortable(arg0) end

---@public
---@param arg0 float
---@return void
function DeviceData:setUseDelta(arg0) end

---@public
---@return boolean
function DeviceData:getIsBatteryPowered() end

---@public
---@param arg0 String
---@param arg1 boolean
---@return void
function DeviceData:playSoundSend(arg0, arg1) end

---@public
---@return BaseSoundEmitter
function DeviceData:getEmitter() end

---@protected
---@return Object
function DeviceData:clone() end

---@public
---@return int
function DeviceData:getTransmitRange() end

---@public
---@param arg0 boolean
---@param arg1 boolean
---@return void
function DeviceData:update(arg0, arg1) end

---@public
---@return float
function DeviceData:getDeviceVolume() end

---@public
---@return void
function DeviceData:updateSimple() end

---@public
---@param arg0 int
---@return void
function DeviceData:setTransmitRange(arg0) end

---@public
---@param arg0 WaveSignalDevice
---@return void
function DeviceData:setParent(arg0) end

---@public
---@param arg0 ByteBuffer
---@param arg1 boolean
---@return void
function DeviceData:save(arg0, arg1) end

---@public
---@param arg0 boolean
---@return void
function DeviceData:setIsTelevision(arg0) end

---@public
---@param arg0 int
---@return void
function DeviceData:setHeadphoneType(arg0) end

---@public
---@return int
function DeviceData:getHeadphoneType() end

---@public
---@return int
function DeviceData:getChannel() end

---@public
---@param arg0 float
---@return void
function DeviceData:setDeviceVolume(arg0) end

---@public
---@param arg0 ItemContainer
---@return InventoryItem
function DeviceData:getHeadphones(arg0) end

---@protected
---@return void
function DeviceData:setEmitterAndPos() end
