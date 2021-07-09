--***********************************************************
--**                    THE INDIE STONE                    **
--***********************************************************

require "Map/CGlobalObject"

---@class CMetalDrumGlobalObject : CGlobalObject
CMetalDrumGlobalObject = CGlobalObject:derive("CMetalDrumGlobalObject")

function CMetalDrumGlobalObject:new(luaSystem, isoObject)
	local o = CGlobalObject.new(self, luaSystem, isoObject)
	return o
end

function CMetalDrumGlobalObject:getObject()
	return self:getIsoObject()
end

