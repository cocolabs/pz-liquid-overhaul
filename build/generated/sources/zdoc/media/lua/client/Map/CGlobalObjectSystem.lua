--***********************************************************
--**                    THE INDIE STONE                    **
--***********************************************************

require "ISBaseObject"

---@class CGlobalObjectSystem : ISBaseObject
CGlobalObjectSystem = ISBaseObject:derive("CGlobalObjectSystem")

function CGlobalObjectSystem:noise(message)
	if self.wantNoise then print(self.systemName..': '..message) end
end

function CGlobalObjectSystem:new(name)
	local system = CGlobalObjects.registerSystem(name)
	-- NOTE: The table for this Lua object is the same one the CGlobalObjectSystem
	-- Java object created.  The Java class calls some of this Lua object's methods.
	local o = system:getModData()
	setmetatable(o, self)
	self.__index = self
	o.system = system
	o.systemName = name
	o.wantNoise = getDebug()
	o:initSystem()
	return o
end

function CGlobalObjectSystem:initSystem()
end

function CGlobalObjectSystem:isValidIsoObject(isoObject)
	error "override this method"
end

function CGlobalObjectSystem:getIsoObjectOnSquare(square)
	if not square then return nil end
	for i=1,square:getObjects():size() do
		local isoObject = square:getObjects():get(i-1)
		if self:isValidIsoObject(isoObject) then
			return isoObject
		end
	end
	return nil
end

function CGlobalObjectSystem:getIsoObjectAt(x, y, z)
	local square = getCell():getGridSquare(x, y, z)
	return self:getIsoObjectOnSquare(square)
end

function CGlobalObjectSystem:newLuaObject(isoObject)
	-- Return an object derived from CGlobalObject
	error "override this method"
end

function CGlobalObjectSystem:getLuaObjectAt(x, y, z)
	local isoObject = self:getIsoObjectAt(x, y, z)
	if not isoObject then return nil end
	-- The client doesn't have an SGlobalObjectSystem Java object, so create a
	-- new luaObject every time.
	return self:newLuaObject(isoObject)
end

function CGlobalObjectSystem:getLuaObjectOnSquare(square)
	if not square then return nil end
	return self:getLuaObjectAt(square:getX(), square:getY(), square:getZ())
end

function CGlobalObjectSystem:sendCommand(playerObj, command, args)
	self.system:sendCommand(command, playerObj, args)
end

function CGlobalObjectSystem:OnServerCommand(command, args)
	-- SGlobalObjectSystem:sendCommand() arguments are routed to this method
	-- in both singleplayer *and* multiplayer.
end

local function OnCGlobalObjectSystemInit(luaClass)
	luaClass.instance = luaClass:new()
end

function CGlobalObjectSystem.RegisterSystemClass(luaClass)
	if luaClass == CGlobalObjectSystem then error "replace : with . before RegisterSystemClass" end

	-- This is to support reloading a derived class file in the Lua debugger.
	for i=1,CGlobalObjects.getSystemCount() do
		local system = CGlobalObjects.getSystemByIndex(i-1)
		if system:getModData().Type == luaClass.Type then
			luaClass.instance = system:getModData()
			return
		end
	end

	Events.OnCGlobalObjectSystemInit.Add(function() OnCGlobalObjectSystemInit(luaClass) end)
end

