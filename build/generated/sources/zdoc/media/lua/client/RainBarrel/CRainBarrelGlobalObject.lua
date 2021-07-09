--***********************************************************
--**                    THE INDIE STONE                    **
--***********************************************************

require "Map/CGlobalObject"

---@class CRainBarrelGlobalObject : CGlobalObject
CRainBarrelGlobalObject = CGlobalObject:derive("CRainBarrelGlobalObject")

function CRainBarrelGlobalObject:new(luaSystem, isoObject)
	local o = CGlobalObject.new(self, luaSystem, isoObject)
	return o
end

function CRainBarrelGlobalObject:getObject()
	return self:getIsoObject()
end
