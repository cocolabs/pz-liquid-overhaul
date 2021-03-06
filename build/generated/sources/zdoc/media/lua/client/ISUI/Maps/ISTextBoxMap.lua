--***********************************************************
--**              	  ROBERT JOHNSON                       **
--***********************************************************

---@class ISTextBoxMap : ISCollapsableWindow
ISTextBoxMap = ISCollapsableWindow:derive("ISTextBoxMap");


--************************************************************************--
--** ISTextBoxMap:initialise
--**
--************************************************************************--

function ISTextBoxMap:initialise()
    ISCollapsableWindow.initialise(self);

	local fontHgt = getTextManager():getFontFromEnum(UIFont.Small):getLineHeight()
	local buttonWid1 = getTextManager():MeasureStringX(UIFont.Small, "Ok") + 12
	local buttonWid2 = getTextManager():MeasureStringX(UIFont.Small, "Cancel") + 12
	local buttonWid = math.max(math.max(buttonWid1, buttonWid2), 100)
	local buttonHgt = math.max(fontHgt + 6, 25)
	local padBottom = 10
    local btnHgt2 = 20

    self.yes = ISButton:new((self:getWidth() / 2)  - 5 - buttonWid, self:getHeight() - padBottom - buttonHgt, buttonWid, buttonHgt, getText("UI_Ok"), self, ISTextBoxMap.onClick);
    self.yes.internal = "OK";
    self.yes:initialise();
    self.yes:instantiate();
    self.yes.borderColor = {r=1, g=1, b=1, a=0.1};
    self:addChild(self.yes);

    self.no = ISButton:new((self:getWidth() / 2) + 5, self:getHeight() - padBottom - buttonHgt, buttonWid, buttonHgt, getText("UI_Cancel"), self, ISTextBoxMap.onClick);
    self.no.internal = "CANCEL";
    self.no:initialise();
    self.no:instantiate();
    self.no.borderColor = {r=1, g=1, b=1, a=0.1};
    self:addChild(self.no);

    self.fontHgt = getTextManager():getFontFromEnum(UIFont.Medium):getLineHeight()
    local inset = 2
    local height = inset + self.fontHgt + inset
    self.entry = ISTextEntryBox:new(self.defaultEntryText, self:getWidth() / 2 - ((self:getWidth() - 40) / 2), (self:getHeight() - height) / 2, self:getWidth() - 40, height);
    self.entry.backgroundColor = {r = 1, g = 1, b = 1, a = 0.3};
    self.entry.font = UIFont.Medium
    self.entry:initialise();
    self.entry:instantiate();
    self:addChild(self.entry);

    local inv = self.character:getInventory();

    self.blackColorBtn = ISButton:new(self.entry.x, self.entry.y - 22, btnHgt2, btnHgt2, "", self, ISTextBoxMap.onClick);
    self.blackColorBtn:initialise();
    self.blackColorBtn.internal = "COLOR";
    self.blackColorBtn.backgroundColor = {r = 0, g = 0, b = 0, a = 1};
    self.blackColorBtn.borderColor = {r=1, g=1, b=1, a=0.4};
    self:addChild(self.blackColorBtn);
    self.blackColorBtn.enable = inv:contains("Pen", true);
    if not self.blackColorBtn.enable then
        self.blackColorBtn.tooltip = getText("Tooltip_Map_NeedBlackPen");
    end

    self.greyColorBtn = ISButton:new(self.blackColorBtn.x + self.blackColorBtn.width + 5, self.entry.y - 22, btnHgt2, btnHgt2, "", self, ISTextBoxMap.onClick);
    self.greyColorBtn:initialise();
    self.greyColorBtn.internal = "COLOR";
    self.greyColorBtn.backgroundColor = {r = 0.2, g = 0.2, b = 0.2, a = 1};
    self.greyColorBtn.borderColor = {r=1, g=1, b=1, a=0.4};
    self:addChild(self.greyColorBtn);
    self.greyColorBtn.enable = inv:contains("Pencil", true);
    if not self.greyColorBtn.enable then
        self.greyColorBtn.tooltip = getText("Tooltip_Map_NeedPencil");
    end

    self.redColorBtn = ISButton:new(self.greyColorBtn.x + self.greyColorBtn.width + 5, self.entry.y - 22, btnHgt2, btnHgt2, "", self, ISTextBoxMap.onClick);
    self.redColorBtn:initialise();
    self.redColorBtn.internal = "COLOR";
    self.redColorBtn.backgroundColor = {r = 1, g = 0, b = 0, a = 1};
    self.redColorBtn.borderColor = {r=1, g=1, b=1, a=0.4};
    self:addChild(self.redColorBtn);
    self.redColorBtn.enable = (inv:contains("RedPen", true));
    if not self.redColorBtn.enable then
        self.redColorBtn.tooltip = getText("Tooltip_Map_NeedRedPen");
    end

    self.blueColorBtn = ISButton:new(self.redColorBtn.x + self.redColorBtn.width + 5, self.entry.y - 22, btnHgt2, btnHgt2, "", self, ISTextBoxMap.onClick);
    self.blueColorBtn:initialise();
    self.blueColorBtn.internal = "COLOR";
    self.blueColorBtn.backgroundColor = {r = 0, g = 0, b = 1, a = 1};
    self.blueColorBtn.borderColor = {r=1, g=1, b=1, a=0.4};
    self:addChild(self.blueColorBtn);
    self.blueColorBtn.enable = (inv:contains("BluePen", true));
    if not self.blueColorBtn.enable then
        self.blueColorBtn.tooltip = getText("Tooltip_Map_NeedBluePen");
    end

    if self.blackColorBtn.enable then
        self.currentColor = ColorInfo.new(0, 0, 0,1);
        self.blackColorBtn.borderColor = {r=1, g=1, b=1, a=1};
    elseif self.greyColorBtn.enable then
        self.currentColor = ColorInfo.new(0.2, 0.2, 0.2,1);
        self.greyColorBtn.borderColor = {r=1, g=1, b=1, a=1};
    elseif self.redColorBtn.enable then
        self.currentColor = ColorInfo.new(1, 0, 0,1);
        self.redColorBtn.borderColor = {r=1, g=1, b=1, a=1};
    else
        self.currentColor = ColorInfo.new(0, 0, 1,1);
        self.blueColorBtn.borderColor = {r=1, g=1, b=1, a=1};
    end

    self.entry.javaObject:setTextColor(self.currentColor);
end

function ISTextBoxMap:setOnlyNumbers(onlyNumbers)
    self.entry:setOnlyNumbers(onlyNumbers);
end

function ISTextBoxMap:setValidateFunction(target, func, arg1, arg2)
	self.validateTarget = target
	self.validateFunc = func
	self.validateArgs = { arg1, arg2 }
end

function ISTextBoxMap:setValidateTooltipText(text)
	self.validateTooltipText = text
end

function ISTextBoxMap:destroy()
	UIManager.setShowPausedMessage(true);
	self:setVisible(false);
	self:removeFromUIManager();
	if UIManager.getSpeedControls() then
		UIManager.getSpeedControls():SetCurrentGameSpeed(1);
	end
end

function ISTextBoxMap:onClick(button)
    if button.internal == "COLOR" then
        self.blackColorBtn.borderColor = {r=1, g=1, b=1, a=0.4};
        self.greyColorBtn.borderColor = {r=1, g=1, b=1, a=0.4};
        self.redColorBtn.borderColor = {r=1, g=1, b=1, a=0.4};
        self.blueColorBtn.borderColor = {r=1, g=1, b=1, a=0.4};
        button.borderColor = {r=1, g=1, b=1, a=1};
        self.currentColor = ColorInfo.new(button.backgroundColor.r, button.backgroundColor.g, button.backgroundColor.b,1);
        self.entry.javaObject:setTextColor(self.currentColor);
    else
        self:destroy();
        if self.onclick ~= nil then
            self.onclick(self.target, button, self.param1, self.param2, self.param3, self.param4);
        end
    end
end

function ISTextBoxMap:prerender()
	self:drawRect(0, 0, self.width, self.height, self.backgroundColor.a, self.backgroundColor.r, self.backgroundColor.g, self.backgroundColor.b);
	self:drawRectBorder(0, 0, self.width, self.height, self.borderColor.a, self.borderColor.r, self.borderColor.g, self.borderColor.b);
	self:drawTextCentre(self.text, self:getWidth() / 2, 30, 1, 1, 1, 1, UIFont.Small);

    self:updateButtons();
end

function ISTextBoxMap:updateButtons()
    self.yes:setEnable(true);
    self.yes.tooltip = nil;
    local text = self.entry:getText()
    if self.validateFunc and not self.validateFunc(self.validateTarget, text, self.validateArgs[1], self.validateArgs[2]) then
        self.yes:setEnable(false);
        self.yes.tooltip = self.validateTooltipText;
    end
    if self.maxChars and self.entry:getInternalText():len() > self.maxChars then
        self.yes:setEnable(false);
        self.yes.tooltip = getText("IGUI_TextBox_TooManyChar", self.maxChars);
    end
    if self.noEmpty and string.trim(self.entry:getInternalText()) == "" then
        self.yes:setEnable(false);
        self.yes.tooltip = getText("IGUI_TextBox_CantBeEmpty");
    end
end

--************************************************************************--
--** ISTextBoxMap:render
--**
--************************************************************************--
function ISTextBoxMap:render()

end

function ISTextBoxMap:onGainJoypadFocus(joypadData)
	ISCollapsableWindow.onGainJoypadFocus(self, joypadData)
	self:setISButtonForA(self.yes)
	self:setISButtonForB(self.no)
	self.joypadButtons = {}
end

function ISTextBoxMap:onJoypadDown(button, joypadData)
	if button == Joypad.AButton then
		if not self.yes:isEnabled() then
			return
		end
		self.yes.player = self.player
		self.yes.onclick(self.yes.target, self.yes)
		if joypadData.focus == self then
			if self.player and JoypadState.players[self.player+1] then
				setJoypadFocus(self.player, nil)
			else
				joypadData.focus = nil
			end
		end
		self:destroy()
	end
	if button == Joypad.BButton then
		self.no.player = self.player
		self.no.onclick(self.no.target, self.no)
		if joypadData.focus == self then
			if self.player and JoypadState.players[self.player+1] then
				setJoypadFocus(self.player, nil)
			else
				joypadData.focus = nil
			end
		end
		self:destroy()
	end
end

--************************************************************************--
--** ISTextBoxMap:new
--**
--************************************************************************--
function ISTextBoxMap:new(x, y, width, height, text, defaultEntryText, target, onclick, player, param1, param2, param3, param4)
	local o = {}
	o = ISCollapsableWindow:new(x, y, width, height);
	setmetatable(o, self)
    self.__index = self
	local playerObj = player and getSpecificPlayer(player) or nil
	if y == 0 then
		if playerObj and playerObj:getJoypadBind() ~= -1 then
			o.y = getPlayerScreenTop(player) + (getPlayerScreenHeight(player) - height) / 2
		else
			o.y = o:getMouseY() - (height / 2)
		end
		o:setY(o.y)
	end
	if x == 0 then
		if playerObj and playerObj:getJoypadBind() ~= -1 then
			o.x = getPlayerScreenLeft(player) + (getPlayerScreenWidth(player) - width) / 2
		else
			o.x = o:getMouseX() - (width / 2)
		end
		o:setX(o.x)
    end
    o.character = playerObj;
	o.name = nil;
    o.backgroundColor = {r=0, g=0, b=0, a=0.5};
    o.borderColor = {r=0.4, g=0.4, b=0.4, a=1};
	o.width = width;
    local txtWidth = getTextManager():MeasureStringX(UIFont.Small, text) + 10;
    if width < txtWidth then
        o.width = txtWidth;
    end
	o.height = height;
	o.anchorLeft = true;
	o.anchorRight = true;
	o.anchorTop = true;
	o.anchorBottom = true;
	o.text = text;
	o.target = target;
	o.onclick = onclick;
	o.player = player
    o.param1 = param1;
    o.param2 = param2;
    o.param3 = param3;
    o.param4 = param4;
    o.defaultEntryText = defaultEntryText;
    return o;
end

function ISTextBoxMap:close()
	ISCollapsableWindow.close(self)
	if JoypadState.players[self.player+1] then
		setJoypadFocus(self.player, nil)
	end
end

