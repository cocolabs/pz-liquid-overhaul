---@class Method : java.lang.reflect.Method
---@field private clazz Class|Unknown
---@field private slot int
---@field private name String
---@field private returnType Class|Unknown
---@field private parameterTypes Class[]
---@field private exceptionTypes Class[]
---@field private modifiers int
---@field private signature String
---@field private genericInfo MethodRepository
---@field private annotations byte[]
---@field private parameterAnnotations byte[]
---@field private annotationDefault byte[]
---@field private methodAccessor MethodAccessor
---@field private root Method
Method = {}

---@public
---@return Class[]
function Method:getExceptionTypes() end

---@return MethodRepository
function Method:getGenericInfo() end

---@public
---@return boolean
function Method:isVarArgs() end

---@param arg0 int
---@param arg1 int
---@return void
function Method:handleParameterNumberMismatch(arg0, arg1) end

---@public
---@return Class|Unknown
function Method:getDeclaringClass() end

---@private
---@return MethodAccessor
function Method:acquireMethodAccessor() end

---@public
---@return Class|Unknown
function Method:getReturnType() end

---@public
---@return String
function Method:toGenericString() end

---@return boolean
function Method:hasGenericInformation() end

---@param arg0 StringBuilder
---@return void
function Method:specificToStringHeader(arg0) end

---@return Method
function Method:copy() end

---@param arg0 MethodAccessor
---@return void
function Method:setMethodAccessor(arg0) end

---@public
---@return Type
function Method:getGenericReturnType() end

---@private
---@return String
function Method:getGenericSignature() end

---@public
---@return AnnotatedType
function Method:getAnnotatedReturnType() end

---@public
---@return Annotation[][]
function Method:getParameterAnnotations() end

---@public
---@param arg0 Class|Unknown
---@return Annotation
function Method:getAnnotation(arg0) end

---@public
---@param arg0 Object
---@return boolean
function Method:equals(arg0) end

---@public
---@return int
function Method:hashCode() end

---@public
---@return Type[]
function Method:getGenericParameterTypes() end

---@public
---@return boolean
function Method:isSynthetic() end

---@public
---@return int
function Method:getParameterCount() end

---@private
---@return GenericsFactory
function Method:getFactory() end

---@public
---@return String
function Method:toString() end

---@public
---@return int
function Method:getModifiers() end

---@public
---@return Type[]
function Method:getGenericExceptionTypes() end

---@public
---@return boolean
function Method:isDefault() end

---@return byte[]
function Method:getAnnotationBytes() end

---@public
---@param arg0 Object
---@param arg1 Object[]
---@return Object
function Method:invoke(arg0, arg1) end

---@public
---@return boolean
function Method:isBridge() end

---@public
---@return String
function Method:getName() end

---@public
---@return Class[]
function Method:getParameterTypes() end

---@return Executable
function Method:getRoot() end

---@return MethodAccessor
function Method:getMethodAccessor() end

---@param arg0 StringBuilder
---@return void
function Method:specificToGenericStringHeader(arg0) end

---@public
---@return Annotation[]
function Method:getDeclaredAnnotations() end

---@public
---@return TypeVariable[]
function Method:getTypeParameters() end

---@public
---@return Object
function Method:getDefaultValue() end
