--
-- Created by IntelliJ IDEA.
-- User: RJ
-- Date: 20/02/14
-- Time: 11:29
-- To change this template use File | Settings | File Templates.
--
---@class ISSafetyUI : ISPanel
ISSafetyUI = ISPanel:derive("ISSafetyUI");

function ISSafetyUI:initialise()
    ISPanel.initialise(self);
end


ISSafetyUI.initUI = function()
    if not isClient() then return end
    if getServerOptions():getBoolean("SafetySystem") then
        Events.OnKeyPressed.Add(ISSafetyUI.onKeyPressed);
    end
end

function ISSafetyUI:render()
    local screenX = getPlayerScreenLeft(self.playerNum)
    local screenWid = getPlayerScreenWidth(self.playerNum)
    self:setX(screenX + screenWid - 150)
    if NonPvpZone.getNonPvpZone(self.character:getX(), self.character:getY()) then
        self.nonPvpZone = true;
        local textWid = getTextManager():MeasureStringX(UIFont.Small, getText("IGUI_PvpZone_NonPvpZone"))
        local textX = math.min(self.x + self.width/2 - textWid/2, screenX + screenWid - 20 - textWid)
        self:drawText(getText("IGUI_PvpZone_NonPvpZone"), textX - self.x, - 20, 1, 0, 0, 1, self.Small);
        if self.safetyEnabled then
            self:drawTexture(self.disableTexture, 0,0,1,1,1,1);
            if not self.character:isSafety() then
                self.character:setSafety(true);
                toggleSafetyServer(self.character);
            end
        end
    elseif self.safetyEnabled then
        self.nonPvpZone = false;
        if self.character:getSafetyCooldown() == 0 then
            if self.character:isSafety() then
                self:drawTexture(self.onTexture, 0,0,1,1,1,1);
            else
                self:drawTexture(self.offTexture,0,0,1,1,1,1);
            end
        else
            self:drawTexture(self.pendingTexture,0,0,1,1,1,1);
            self:drawTextCentre(tostring(math.ceil(self.character:getSafetyCooldown())), self:getWidth() / 2, (self:getHeight() / 2) - 10, 1,1,1,1, UIFont.Small);
        end
    end
end

ISSafetyUI.onKeyPressed = function(key)
    if key == getCore():getKey("Toggle Safety") then
        if getPlayerSafetyUI(0) then
            getPlayerSafetyUI(0):toggleSafety()
        end
    end
end

function ISSafetyUI:toggleSafety()
    if getServerOptions():getBoolean("SafetySystem") and self.character:getSafetyCooldown() == 0 then
        ISTimedActionQueue.clear(self.character);
        ISTimedActionQueue.add(ISToggleSafetyAction:new(self.character));
        self.character:setSafetyCooldown(getServerOptions():getInteger("SafetyCooldownTimer") + getServerOptions():getInteger("SafetyToggleTimer"));
    end
end

function ISSafetyUI:onMouseUp(x, y)
    if not self.nonPvpZone and self.safetyEnabled then
        self:toggleSafety()
    end
end

function ISSafetyUI:new(x, y, playerNum)
    local onTexture = getTexture("media/ui/SafetyON.png");
    local o = ISPanel:new(x, y, onTexture:getWidth(), onTexture:getHeight());
    setmetatable(o, self)
    self.__index = self
    o.x = x;
    o.y = y;
    o.borderColor = {r=0, g=0, b=0, a=0};
    o.backgroundColor = {r=0, g=0, b=0, a=0};
    o.width = onTexture:getWidth();
    o.height = onTexture:getHeight();
    o.anchorLeft = true;
    o.anchorRight = false;
    o.anchorTop = true;
    o.safetyEnabled = getServerOptions():getBoolean("SafetySystem");
    o.anchorBottom = false;
    --    o.pvpText = "Disabled";
    o.offTexture = getTexture("media/ui/SafetyOFF.png");
    o.pendingTexture = getTexture("media/ui/SafetyPEND.png");
    o.disableTexture = getTexture("media/ui/SafetyDISABLE.png");
    o.onTexture = onTexture;
    o.noBackground = true;
    o.nonPvpZone = false;
    o.playerNum = playerNum
    o.character = getSpecificPlayer(playerNum)
    return o
end

-- Give the new players the SpawnItem if configured
ISSafetyUI.onNewGame = function(playerObj, playerSquare)
    if not isClient() then return end
    if getServerOptions():getOption("SpawnItems") and getServerOptions():getOption("SpawnItems")~= "" then
        local items = luautils.split(getServerOptions():getOption("SpawnItems"), ",");
        for i,v in pairs(items) do
            playerObj:getInventory():AddItem(v);
        end
    end
end

Events.OnNewGame.Add(ISSafetyUI.onNewGame);
Events.OnGameStart.Add(ISSafetyUI.initUI);

if not isClient() then
    Events.OnKeyPressed.Add(
        function(key)
            if key == getCore():getKey("Toggle Safety") then
                IsoPlayer.setCoopPVP(not IsoPlayer.getCoopPVP())
            end
        end
    )
end
