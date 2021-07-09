---@class Clock : zombie.ui.Clock
---@field digits Texture[]
---@field texture Texture
---@field slash Texture
---@field colon Texture
---@field texAM Texture
---@field texPM Texture
---@field public digital boolean
---@field private clockPlayer IsoPlayer
---@field public instance Clock
---@field private cacheTemp String
---@field private cacheTempCntr int
Clock = {}

---@public
---@return boolean
function Clock:isDateVisible() end

---Overrides:
---
---render in class UIElement
---@public
---@return void
function Clock:render() end

---@public
---@return void
function Clock:resize() end
