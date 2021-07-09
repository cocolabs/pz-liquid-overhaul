---@class IsoBrokenGlass : zombie.iso.objects.IsoBrokenGlass
IsoBrokenGlass = {}

---@public
---@param arg0 float
---@param arg1 float
---@param arg2 float
---@param arg3 ColorInfo
---@return void
function IsoBrokenGlass:renderObjectPicker(arg0, arg1, arg2, arg3) end

---@public
---@return void
function IsoBrokenGlass:removeFromWorld() end

---@public
---@return String
function IsoBrokenGlass:getObjectName() end

---@public
---@param arg0 float
---@param arg1 float
---@param arg2 float
---@param arg3 ColorInfo
---@param arg4 boolean
---@param arg5 boolean
---@param arg6 Shader
---@return void
function IsoBrokenGlass:render(arg0, arg1, arg2, arg3, arg4, arg5, arg6) end

---@public
---@param arg0 ByteBuffer
---@return void
function IsoBrokenGlass:save(arg0) end

---@public
---@param arg0 ByteBuffer
---@param arg1 int
---@return void
function IsoBrokenGlass:load(arg0, arg1) end

---@public
---@return void
function IsoBrokenGlass:addToWorld() end
