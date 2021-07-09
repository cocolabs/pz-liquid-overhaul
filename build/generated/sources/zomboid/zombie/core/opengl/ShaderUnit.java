package zombie.core.opengl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import org.lwjgl.opengl.ARBShaderObjects;
import zombie.core.IndieFileLoader;
import zombie.debug.DebugLog;
import zombie.debug.DebugType;
import zombie.util.StringUtils;

public final class ShaderUnit {
   private final ShaderProgram m_parentProgram;
   private final String m_fileName;
   private final ShaderUnit.Type m_unitType;
   private int m_glID;
   private boolean m_isAttached;

   public ShaderUnit(ShaderProgram var1, String var2, ShaderUnit.Type var3) {
      this.m_parentProgram = var1;
      this.m_fileName = var2;
      this.m_unitType = var3;
      this.m_glID = 0;
      this.m_isAttached = false;
   }

   public String getFileName() {
      return this.m_fileName;
   }

   public boolean isCompiled() {
      return this.m_glID != 0;
   }

   public boolean compile() {
      if (DebugLog.isEnabled(DebugType.Shader)) {
         DebugLog.Shader.debugln(this.getFileName());
      }

      int var1 = getGlType(this.m_unitType);
      ArrayList var2 = new ArrayList();
      String var3 = this.loadShaderFile(this.m_fileName, var2);
      if (var3 == null) {
         return false;
      } else {
         Iterator var4 = var2.iterator();

         while(var4.hasNext()) {
            String var5 = (String)var4.next();
            if (this.m_parentProgram == null) {
               DebugLog.Shader.error(this.getFileName() + "> Cannot include additional shader file. Parent program is null. " + var5);
               break;
            }

            String var6 = var5 + ".glsl";
            if (DebugLog.isEnabled(DebugType.Shader)) {
               DebugLog.Shader.debugln(this.getFileName() + "> Loading additional shader unit: " + var6);
            }

            ShaderUnit var7 = this.m_parentProgram.addShader(var6, this.m_unitType);
            if (!var7.isCompiled() && !var7.compile()) {
               DebugLog.Shader.error(this.getFileName() + "> Included shader unit failed to compile: " + var6);
               return false;
            }
         }

         int var8 = ARBShaderObjects.glCreateShaderObjectARB(var1);
         if (var8 == 0) {
            DebugLog.Shader.error(this.getFileName() + "> Failed to generate shaderID. Shader code:\n" + var3);
            return false;
         } else {
            ARBShaderObjects.glShaderSourceARB(var8, var3);
            ARBShaderObjects.glCompileShaderARB(var8);
            ShaderProgram.printLogInfo(var8);
            this.m_glID = var8;
            return true;
         }
      }
   }

   public boolean attach() {
      if (DebugLog.isEnabled(DebugType.Shader)) {
         DebugLog.Shader.debugln(this.getFileName());
      }

      if (this.getParentShaderProgramGLID() == 0) {
         DebugLog.Shader.error("Parent program does not exist.");
         return false;
      } else {
         if (!this.isCompiled()) {
            this.compile();
         }

         if (!this.isCompiled()) {
            return false;
         } else {
            ARBShaderObjects.glAttachObjectARB(this.getParentShaderProgramGLID(), this.getGLID());
            if (!PZGLUtil.checkGLError(false)) {
               this.destroy();
               return false;
            } else {
               this.m_isAttached = true;
               return true;
            }
         }
      }
   }

   public void destroy() {
      if (this.m_glID == 0) {
         this.m_isAttached = false;
      } else {
         DebugLog.Shader.debugln(this.getFileName());

         try {
            if (this.m_isAttached && this.getParentShaderProgramGLID() != 0) {
               ARBShaderObjects.glDetachObjectARB(this.getParentShaderProgramGLID(), this.m_glID);
               if (!PZGLUtil.checkGLError(false)) {
                  DebugLog.Shader.error("ShaderUnit failed to detach: " + this.getFileName());
                  return;
               }
            }

            ARBShaderObjects.glDeleteObjectARB(this.m_glID);
            PZGLUtil.checkGLError(false);
         } finally {
            this.m_glID = 0;
            this.m_isAttached = false;
         }

      }
   }

   public int getGLID() {
      return this.m_glID;
   }

   public int getParentShaderProgramGLID() {
      return this.m_parentProgram != null ? this.m_parentProgram.getShaderID() : 0;
   }

   private static int getGlType(ShaderUnit.Type var0) {
      return var0 == ShaderUnit.Type.Vert ? '謱' : '謰';
   }

   private String loadShaderFile(String var1, ArrayList var2) {
      var2.clear();
      String var3 = this.preProcessShaderFile(var1, var2);
      if (var3 == null) {
         return null;
      } else {
         int var4 = var3.indexOf("#");
         if (var4 > 0) {
            var3 = var3.substring(var4);
         }

         return var3;
      }
   }

   private String preProcessShaderFile(String var1, ArrayList var2) {
      StringBuilder var3 = new StringBuilder();

      try {
         InputStreamReader var4 = IndieFileLoader.getStreamReader(var1, false);
         Throwable var5 = null;

         try {
            BufferedReader var6 = new BufferedReader(var4);
            Throwable var7 = null;

            try {
               String var8 = System.getProperty("line.separator");

               for(String var9 = var6.readLine(); var9 != null; var9 = var6.readLine()) {
                  String var10 = var9.trim();
                  if (!var10.startsWith("#include ") || !this.processIncludeLine(var1, var3, var10, var8, var2)) {
                     var3.append(var10).append(var8);
                  }
               }
            } catch (Throwable var34) {
               var7 = var34;
               throw var34;
            } finally {
               if (var6 != null) {
                  if (var7 != null) {
                     try {
                        var6.close();
                     } catch (Throwable var33) {
                        var7.addSuppressed(var33);
                     }
                  } else {
                     var6.close();
                  }
               }

            }
         } catch (Throwable var36) {
            var5 = var36;
            throw var36;
         } finally {
            if (var4 != null) {
               if (var5 != null) {
                  try {
                     var4.close();
                  } catch (Throwable var32) {
                     var5.addSuppressed(var32);
                  }
               } else {
                  var4.close();
               }
            }

         }
      } catch (Exception var38) {
         DebugLog.Shader.error("Failed reading shader code. fileName:" + var1);
         var38.printStackTrace(DebugLog.Shader);
         return null;
      }

      return var3.toString();
   }

   private boolean processIncludeLine(String var1, StringBuilder var2, String var3, String var4, ArrayList var5) {
      String var6 = var3.substring("#include ".length());
      if (var6.startsWith("\"") && var6.endsWith("\"")) {
         String var7 = this.getParentFolder(var1);
         String var8 = var6.substring(1, var6.length() - 1);
         var8 = var8.trim();
         var8 = var8.replace('\\', '/');
         var8 = var8.toLowerCase();
         if (var8.contains(":")) {
            DebugLog.Shader.error(var1 + "> include cannot have ':' characters. " + var6);
            return false;
         } else if (var8.startsWith("/")) {
            DebugLog.Shader.error(var1 + "> include cannot start with '/' or '\\' characters. " + var6);
            return false;
         } else {
            String var9 = var7 + "/" + var8;
            ArrayList var10 = new ArrayList();
            String[] var11 = var9.split("/");
            int var12 = var11.length;

            String var14;
            for(int var13 = 0; var13 < var12; ++var13) {
               var14 = var11[var13];
               if (!var14.equals(".") && !var14.isEmpty()) {
                  if (StringUtils.isNullOrWhitespace(var14)) {
                     DebugLog.Shader.error(var1 + "> include path cannot have whitespace-only folders. " + var6);
                     return false;
                  }

                  if (var14.equals("..")) {
                     if (var10.isEmpty()) {
                        DebugLog.Shader.error(var1 + "> include cannot go out of bounds with '..' parameters. " + var6);
                        return false;
                     }

                     var10.remove(var10.size() - 1);
                  } else {
                     var10.add(var14);
                  }
               }
            }

            StringBuilder var15 = new StringBuilder(var9.length());

            String var18;
            for(Iterator var16 = var10.iterator(); var16.hasNext(); var15.append(var18)) {
               var18 = (String)var16.next();
               if (var15.length() > 0) {
                  var15.append('/');
               }
            }

            String var17 = var15.toString();
            if (var5.contains(var17)) {
               var2.append("// Duplicate Include, skipped. ").append(var3).append(var4);
               return true;
            } else {
               var5.add(var17);
               var18 = var17 + ".h";
               var14 = this.preProcessShaderFile(var18, var5);
               var2.append(var4);
               var2.append("// Include begin ").append(var3).append(var4);
               var2.append(var14).append(var4);
               var2.append("// Include end   ").append(var3).append(var4);
               var2.append(var4);
               return true;
            }
         }
      } else {
         DebugLog.Shader.error(var1 + "> include needs to be in quotes: " + var6);
         return false;
      }
   }

   private String getParentFolder(String var1) {
      int var2 = var1.lastIndexOf("/");
      if (var2 > -1) {
         return var1.substring(0, var2);
      } else {
         var2 = var1.lastIndexOf("\\");
         return var2 > -1 ? var1.substring(0, var2) : "";
      }
   }

   public static enum Type {
      Vert,
      Frag;
   }
}
