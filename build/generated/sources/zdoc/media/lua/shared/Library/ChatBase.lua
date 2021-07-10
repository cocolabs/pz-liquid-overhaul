---@class ChatBase : zombie.chat.ChatBase
---@field private ID_NOT_SET int
---@field private id int
---@field private titleID String
---@field private type ChatType
---@field private settings ChatSettings
---@field private customSettings boolean
---@field private chatTab ChatTab
---@field private translatedTitle String
---@field protected members ArrayList|Unknown
---@field private justAddedMembers ArrayList|Unknown
---@field private justRemovedMembers ArrayList|Unknown
---@field protected messages ArrayList|Unknown
---@field private serverConnection UdpConnection
---@field private mode ChatMode
---@field private chatOwner IsoPlayer
---@field private memberLock Lock
ChatBase = {}

---@public
---@param arg0 ChatMessage
---@return void
---@overload fun(arg0:ServerChatMessage)
function ChatBase:sendMessageToChatMembers(arg0) end

---@public
---@param arg0 ServerChatMessage
---@return void
function ChatBase:sendMessageToChatMembers(arg0) end

---@public
---@return int
function ChatBase:getID() end

---@public
---@param arg0 ChatMessage
---@return String
function ChatBase:getMessageTextWithPrefix(arg0) end

---@public
---@param arg0 ChatMessage
---@return void
function ChatBase:sendToServer(arg0) end

---@public
---@param arg0 ChatMessage
---@return String
function ChatBase:getMessagePrefix(arg0) end

---@public
---@param arg0 int
---@return void
function ChatBase:addMember(arg0) end

---@public
---@return boolean
function ChatBase:isSendingToRadio() end

---@protected
---@return IsoPlayer
function ChatBase:getChatOwner() end

---@protected
---@param arg0 ByteBufferWriter
---@return void
function ChatBase:packChat(arg0) end

---@private
---@param arg0 ArrayList|Unknown
---@return void
function ChatBase:syncMembers(arg0) end

---@public
---@param arg0 Integer
---@return void
function ChatBase:leaveMember(arg0) end

---@public
---@param arg0 UdpConnection
---@return void
---@overload fun(arg0:Integer)
function ChatBase:sendPlayerLeaveChatPacket(arg0) end

---@public
---@param arg0 Integer
---@return void
function ChatBase:sendPlayerLeaveChatPacket(arg0) end

---@protected
---@return boolean
function ChatBase:isShowAuthor() end

---@public
---@param arg0 String
---@return void
function ChatBase:setFontSize(arg0) end

---@protected
---@return boolean
function ChatBase:isAllowChatIcons() end

---@protected
---@return boolean
function ChatBase:isAllowBBcode() end

---@public
---@param arg0 boolean
---@return void
function ChatBase:setShowTimestamp(arg0) end

---@private
---@param arg0 int
---@return boolean
function ChatBase:hasMember(arg0) end

---@protected
---@return boolean
function ChatBase:isShowTimestamp() end

---@protected
---@return boolean
function ChatBase:isEqualizeLineHeights() end

---@public
---@param arg0 int
---@param arg1 ChatMessage
---@return void
---@overload fun(arg0:UdpConnection, arg1:ChatMessage)
function ChatBase:sendMessageToPlayer(arg0, arg1) end

---@public
---@param arg0 UdpConnection
---@param arg1 ChatMessage
---@return void
function ChatBase:sendMessageToPlayer(arg0, arg1) end

---@protected
---@return boolean
function ChatBase:hasChatTab() end

---@public
---@param arg0 Integer
---@return void
function ChatBase:removeMember(arg0) end

---@public
---@return float
function ChatBase:getRange() end

---@public
---@param arg0 String
---@return ChatMessage
---@overload fun(arg0:String, arg1:String)
function ChatBase:createMessage(arg0) end

---@private
---@param arg0 String
---@param arg1 String
---@return ChatMessage
function ChatBase:createMessage(arg0, arg1) end

---@public
---@param arg0 ChatMessage
---@return void
---@overload fun(arg0:String, arg1:String)
function ChatBase:showMessage(arg0) end

---@public
---@param arg0 String
---@param arg1 String
---@return void
function ChatBase:showMessage(arg0, arg1) end

---@protected
---@return boolean
function ChatBase:isAllowImages() end

---@private
---@param arg0 short
---@param arg1 UdpConnection
---@param arg2 ChatMessage
---@return void
function ChatBase:sendMessagePacket(arg0, arg1, arg2) end

---@protected
---@return String
function ChatBase:getTitle() end

---@protected
---@return String
---@overload fun(arg0:Color)
function ChatBase:getColorTag() end

---@protected
---@param arg0 Color
---@return String
function ChatBase:getColorTag(arg0) end

---@protected
---@return boolean
function ChatBase:isShowTitle() end

---@public
---@param arg0 String
---@return ServerChatMessage
function ChatBase:createServerMessage(arg0) end

---@protected
---@return boolean
function ChatBase:isAllowFonts() end

---@public
---@return short
function ChatBase:getTabID() end

---@public
---@return ArrayList|Unknown
function ChatBase:getJustAddedMembers() end

---@public
---@return void
function ChatBase:close() end

---@public
---@param arg0 ByteBuffer
---@return ChatMessage
function ChatBase:unpackMessage(arg0) end

---@protected
---@return String
function ChatBase:getFontSize() end

---@public
---@param arg0 ChatSettings
---@return void
function ChatBase:setSettings(arg0) end

---@protected
---@return String
function ChatBase:getChatSettingsTags() end

---@public
---@param arg0 boolean
---@return void
function ChatBase:setShowTitle(arg0) end

---@protected
---@return boolean
function ChatBase:isAllowColors() end

---@public
---@param arg0 ArrayList|Unknown
---@return void
function ChatBase:syncMembersByUsernames(arg0) end

---@public
---@param arg0 ByteBufferWriter
---@param arg1 ChatMessage
---@return void
function ChatBase:packMessage(arg0, arg1) end

---@public
---@return boolean
function ChatBase:isEnabled() end

---@public
---@return float
function ChatBase:getZombieAttractionRange() end

---@public
---@param arg0 UdpConnection
---@return void
function ChatBase:sendPlayerJoinChatPacket(arg0) end

---@public
---@return ArrayList|Unknown
function ChatBase:getJustRemovedMembers() end

---@public
---@return ChatType
function ChatBase:getType() end

---@protected
---@return String
function ChatBase:getFontSizeTag() end

---@public
---@return Color
function ChatBase:getColor() end

---@protected
---@return boolean
function ChatBase:isCustomSettings() end

---@protected
---@return String
function ChatBase:getChatOwnerName() end

---@public
---@return String
function ChatBase:getTitleID() end

---@public
---@return ChatMode
function ChatBase:getMode() end