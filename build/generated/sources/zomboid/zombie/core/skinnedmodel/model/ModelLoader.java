package zombie.core.skinnedmodel.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;
import zombie.core.opengl.RenderThread;
import zombie.core.skinnedmodel.ModelManager;
import zombie.core.skinnedmodel.animation.AnimationClip;
import zombie.core.skinnedmodel.animation.Keyframe;
import zombie.core.skinnedmodel.animation.StaticAnimation;
import zombie.util.SharedStrings;

public final class ModelLoader {
   public static final ModelLoader instance = new ModelLoader();
   private final ThreadLocal sharedStrings = ThreadLocal.withInitial(SharedStrings::new);

   protected ModelTxt loadTxt(String var1, boolean var2, boolean var3, SkinningData var4) throws IOException {
      ModelTxt var5 = new ModelTxt();
      var5.bStatic = var2;
      var5.bReverse = var3;
      VertexBufferObject.VertexFormat var6 = new VertexBufferObject.VertexFormat(var2 ? 4 : 6);
      var6.setElement(0, VertexBufferObject.VertexType.VertexArray, 12);
      var6.setElement(1, VertexBufferObject.VertexType.NormalArray, 12);
      var6.setElement(2, VertexBufferObject.VertexType.TangentArray, 12);
      var6.setElement(3, VertexBufferObject.VertexType.TextureCoordArray, 8);
      if (!var2) {
         var6.setElement(4, VertexBufferObject.VertexType.BlendWeightArray, 16);
         var6.setElement(5, VertexBufferObject.VertexType.BlendIndexArray, 16);
      }

      var6.calculate();
      FileReader var7 = new FileReader(var1);
      Throwable var8 = null;

      try {
         BufferedReader var9 = new BufferedReader(var7);
         Throwable var10 = null;

         try {
            SharedStrings var11 = (SharedStrings)this.sharedStrings.get();
            ModelLoader.LoadMode var12 = ModelLoader.LoadMode.Version;
            String var13 = null;
            int var14 = 0;
            int var15 = 0;
            int var16 = 0;
            boolean var17 = false;
            int var18 = 0;
            boolean var19 = false;

            while(true) {
               while(true) {
                  int var23;
                  int var24;
                  int var71;
                  int var74;
                  do {
                     if ((var13 = var9.readLine()) == null) {
                        if (!var2 && var4 != null) {
                           try {
                              int[] var76 = new int[var5.boneIndices.size()];
                              ArrayList var79 = var5.SkeletonHierarchy;
                              HashMap var77 = var5.boneIndices;
                              HashMap var81 = new HashMap(var4.BoneIndices);
                              ArrayList var84 = new ArrayList(var4.SkeletonHierarchy);
                              var77.forEach((var4x, var5x) -> {
                                 int var6 = (Integer)var81.getOrDefault(var4x, -1);
                                 if (var6 == -1) {
                                    var6 = var81.size();
                                    var81.put(var4x, var6);
                                    int var7 = (Integer)var79.get(var5x);
                                    if (var7 >= 0) {
                                       var84.add(var76[var7]);
                                    } else {
                                       boolean var8 = true;
                                    }
                                 }

                                 var76[var5x] = var6;
                              });
                              var5.boneIndices = var81;
                              var5.SkeletonHierarchy = var84;

                              int var85;
                              for(var71 = 0; var71 < var5.vertices.m_numVertices; ++var71) {
                                 var74 = (int)var5.vertices.getElementFloat(var71, 5, 0);
                                 var23 = (int)var5.vertices.getElementFloat(var71, 5, 1);
                                 var24 = (int)var5.vertices.getElementFloat(var71, 5, 2);
                                 var85 = (int)var5.vertices.getElementFloat(var71, 5, 3);
                                 if (var74 >= 0) {
                                    var74 = var76[var74];
                                 }

                                 if (var23 >= 0) {
                                    var23 = var76[var23];
                                 }

                                 if (var24 >= 0) {
                                    var24 = var76[var24];
                                 }

                                 if (var85 >= 0) {
                                    var85 = var76[var85];
                                 }

                                 var5.vertices.setElement(var71, 5, (float)var74, (float)var23, (float)var24, (float)var85);
                              }

                              Iterator var87 = var5.clips.values().iterator();

                              while(var87.hasNext()) {
                                 AnimationClip var86 = (AnimationClip)var87.next();
                                 Keyframe[] var89 = var86.getKeyframes();
                                 var24 = var89.length;

                                 for(var85 = 0; var85 < var24; ++var85) {
                                    Keyframe var90 = var89[var85];
                                    var90.Bone = var76[var90.Bone];
                                 }
                              }

                              var5.skinOffsetMatrices = this.RemapMatrices(var76, var5.skinOffsetMatrices, var5.boneIndices.size());
                              var5.bindPose = this.RemapMatrices(var76, var5.bindPose, var5.boneIndices.size());
                              var5.invBindPose = this.RemapMatrices(var76, var5.invBindPose, var5.boneIndices.size());
                           } catch (Exception var64) {
                              var64.toString();
                           }

                           return var5;
                        }

                        return var5;
                     }
                  } while(var13.indexOf(35) == 0);

                  if (var13.contains("Tangent")) {
                     if (var2) {
                        var14 += 2;
                     }

                     var19 = true;
                  }

                  if (var14 > 0) {
                     --var14;
                  } else {
                     String var21;
                     float var22;
                     float var28;
                     int var70;
                     String var72;
                     String[] var73;
                     String var75;
                     switch(var12) {
                     case Version:
                        var12 = ModelLoader.LoadMode.ModelName;
                        break;
                     case ModelName:
                        var12 = ModelLoader.LoadMode.VertexStrideElementCount;
                        break;
                     case VertexStrideElementCount:
                        var12 = ModelLoader.LoadMode.VertexCount;
                        if (var2) {
                           var14 = 7;
                        } else {
                           var14 = 13;
                        }
                        break;
                     case VertexCount:
                        var15 = Integer.parseInt(var13);
                        var12 = ModelLoader.LoadMode.VertexBuffer;
                        var5.vertices = new VertexBufferObject.VertexArray(var6, var15);
                        break;
                     case VertexBuffer:
                        var70 = 0;

                        for(; var70 < var15; ++var70) {
                           var73 = var13.split(",");
                           var22 = Float.parseFloat(var73[0].trim());
                           float var80 = Float.parseFloat(var73[1].trim());
                           float var82 = Float.parseFloat(var73[2].trim());
                           var13 = var9.readLine();
                           var73 = var13.split(",");
                           float var83 = Float.parseFloat(var73[0].trim());
                           float var88 = Float.parseFloat(var73[1].trim());
                           float var91 = Float.parseFloat(var73[2].trim());
                           var28 = 0.0F;
                           float var92 = 0.0F;
                           float var93 = 0.0F;
                           if (var19) {
                              var13 = var9.readLine();
                              var73 = var13.split(",");
                              var28 = Float.parseFloat(var73[0].trim());
                              var92 = Float.parseFloat(var73[1].trim());
                              var93 = Float.parseFloat(var73[2].trim());
                           }

                           var13 = var9.readLine();
                           var73 = var13.split(",");
                           float var94 = Float.parseFloat(var73[0].trim());
                           float var32 = Float.parseFloat(var73[1].trim());
                           float var33 = 0.0F;
                           float var34 = 0.0F;
                           float var35 = 0.0F;
                           float var36 = 0.0F;
                           int var37 = 0;
                           int var38 = 0;
                           int var39 = 0;
                           int var40 = 0;
                           if (!var2) {
                              var13 = var9.readLine();
                              var73 = var13.split(",");
                              var33 = Float.parseFloat(var73[0].trim());
                              var34 = Float.parseFloat(var73[1].trim());
                              var35 = Float.parseFloat(var73[2].trim());
                              var36 = Float.parseFloat(var73[3].trim());
                              var13 = var9.readLine();
                              var73 = var13.split(",");
                              var37 = Integer.parseInt(var73[0].trim());
                              var38 = Integer.parseInt(var73[1].trim());
                              var39 = Integer.parseInt(var73[2].trim());
                              var40 = Integer.parseInt(var73[3].trim());
                           }

                           var13 = var9.readLine();
                           var5.vertices.setElement(var70, 0, var22, var80, var82);
                           var5.vertices.setElement(var70, 1, var83, var88, var91);
                           var5.vertices.setElement(var70, 2, var28, var92, var93);
                           var5.vertices.setElement(var70, 3, var94, var32);
                           if (!var2) {
                              var5.vertices.setElement(var70, 4, var33, var34, var35, var36);
                              var5.vertices.setElement(var70, 5, (float)var37, (float)var38, (float)var39, (float)var40);
                           }
                        }

                        var12 = ModelLoader.LoadMode.NumberOfFaces;
                        break;
                     case NumberOfFaces:
                        var16 = Integer.parseInt(var13);
                        var5.elements = new int[var16 * 3];
                        var12 = ModelLoader.LoadMode.FaceData;
                        break;
                     case FaceData:
                        for(var70 = 0; var70 < var16; ++var70) {
                           var73 = var13.split(",");
                           var74 = Integer.parseInt(var73[0].trim());
                           var23 = Integer.parseInt(var73[1].trim());
                           var24 = Integer.parseInt(var73[2].trim());
                           if (var3) {
                              var5.elements[var70 * 3 + 2] = var74;
                              var5.elements[var70 * 3 + 1] = var23;
                              var5.elements[var70 * 3 + 0] = var24;
                           } else {
                              var5.elements[var70 * 3 + 0] = var74;
                              var5.elements[var70 * 3 + 1] = var23;
                              var5.elements[var70 * 3 + 2] = var24;
                           }

                           var13 = var9.readLine();
                        }

                        var12 = ModelLoader.LoadMode.NumberOfBones;
                        break;
                     case NumberOfBones:
                        var18 = Integer.parseInt(var13);
                        var12 = ModelLoader.LoadMode.SkeletonHierarchy;
                        break;
                     case SkeletonHierarchy:
                        for(var70 = 0; var70 < var18; ++var70) {
                           var71 = Integer.parseInt(var13);
                           var13 = var9.readLine();
                           var74 = Integer.parseInt(var13);
                           var13 = var9.readLine();
                           var75 = var11.get(var13);
                           var13 = var9.readLine();
                           var5.SkeletonHierarchy.add(var74);
                           var5.boneIndices.put(var75, var71);
                        }

                        var12 = ModelLoader.LoadMode.BindPose;
                        break;
                     case BindPose:
                        for(var70 = 0; var70 < var18; ++var70) {
                           var13 = var9.readLine();
                           var21 = var9.readLine();
                           var72 = var9.readLine();
                           var75 = var9.readLine();
                           var5.bindPose.add(var70, this.getMatrix(var13, var21, var72, var75));
                           var13 = var9.readLine();
                        }

                        var12 = ModelLoader.LoadMode.InvBindPose;
                        break;
                     case InvBindPose:
                        for(var70 = 0; var70 < var18; ++var70) {
                           var13 = var9.readLine();
                           var21 = var9.readLine();
                           var72 = var9.readLine();
                           var75 = var9.readLine();
                           var5.invBindPose.add(var70, this.getMatrix(var13, var21, var72, var75));
                           var13 = var9.readLine();
                        }

                        var12 = ModelLoader.LoadMode.SkinOffsetMatrices;
                        break;
                     case SkinOffsetMatrices:
                        for(var70 = 0; var70 < var18; ++var70) {
                           var13 = var9.readLine();
                           var21 = var9.readLine();
                           var72 = var9.readLine();
                           var75 = var9.readLine();
                           var5.skinOffsetMatrices.add(var70, this.getMatrix(var13, var21, var72, var75));
                           var13 = var9.readLine();
                        }

                        var12 = ModelLoader.LoadMode.NumberOfAnims;
                        break;
                     case NumberOfAnims:
                        int var69 = Integer.parseInt(var13);
                        var12 = ModelLoader.LoadMode.Anim;
                        break;
                     case Anim:
                        ArrayList var20 = new ArrayList();
                        var21 = var13;
                        var13 = var9.readLine();
                        var22 = Float.parseFloat(var13);
                        var13 = var9.readLine();
                        var23 = Integer.parseInt(var13);
                        var13 = var9.readLine();

                        for(var24 = 0; var24 < var23; ++var24) {
                           Keyframe var25 = new Keyframe();
                           int var26 = Integer.parseInt(var13);
                           var13 = var9.readLine();
                           String var27 = var11.get(var13);
                           var13 = var9.readLine();
                           var28 = Float.parseFloat(var13);
                           var13 = var9.readLine();
                           String var29 = var9.readLine();
                           Vector3f var30 = this.getVector(var13);
                           Quaternion var31 = this.getQuaternion(var29);
                           if (var24 < var23 - 1) {
                              var13 = var9.readLine();
                           }

                           var25.Bone = var26;
                           var25.BoneName = var27;
                           var25.Time = var28;
                           var25.Rotation = var31;
                           var25.Position = new Vector3f(var30);
                           var20.add(var25);
                        }

                        AnimationClip var78 = new AnimationClip(var22, var20, var21, false);
                        var20.clear();
                        if (ModelManager.instance.bCreateSoftwareMeshes) {
                           var78.staticClip = new StaticAnimation(var78);
                        }

                        var5.clips.put(var21, var78);
                     }
                  }
               }
            }
         } catch (Throwable var65) {
            var10 = var65;
            throw var65;
         } finally {
            if (var9 != null) {
               if (var10 != null) {
                  try {
                     var9.close();
                  } catch (Throwable var63) {
                     var10.addSuppressed(var63);
                  }
               } else {
                  var9.close();
               }
            }

         }
      } catch (Throwable var67) {
         var8 = var67;
         throw var67;
      } finally {
         if (var7 != null) {
            if (var8 != null) {
               try {
                  var7.close();
               } catch (Throwable var62) {
                  var8.addSuppressed(var62);
               }
            } else {
               var7.close();
            }
         }

      }
   }

   protected void applyToMesh(ModelTxt var1, ModelMesh var2, SkinningData var3) {
      if (var1.bStatic) {
         if (!ModelManager.NoOpenGL) {
            RenderThread.queueInvokeOnRenderContext(() -> {
               var2.SetVertexBuffer(new VertexBufferObject(var1.vertices, var1.elements));
               if (ModelManager.instance.bCreateSoftwareMeshes) {
                  var2.softwareMesh.vb = var2.vb;
               }

            });
         }
      } else {
         var2.skinningData = new SkinningData(var1.clips, var1.bindPose, var1.invBindPose, var1.skinOffsetMatrices, var1.SkeletonHierarchy, var1.boneIndices);
         if (!ModelManager.NoOpenGL) {
            RenderThread.queueInvokeOnRenderContext(() -> {
               var2.SetVertexBuffer(new VertexBufferObject(var1.vertices, var1.elements, var1.bReverse));
               if (ModelManager.instance.bCreateSoftwareMeshes) {
               }

            });
         }
      }

      if (var3 != null) {
         var2.skinningData.AnimationClips = var3.AnimationClips;
      }

   }

   protected void applyToAnimation(ModelTxt var1, AnimationAsset var2) {
      var2.AnimationClips = var1.clips;
      var2.assetParams.animationsMesh.skinningData.AnimationClips.putAll(var1.clips);
   }

   private ArrayList RemapMatrices(int[] var1, ArrayList var2, int var3) {
      ArrayList var4 = new ArrayList(var3);
      Matrix4f var5 = new Matrix4f();

      int var6;
      for(var6 = 0; var6 < var3; ++var6) {
         var4.add(var5);
      }

      for(var6 = 0; var6 < var1.length; ++var6) {
         var4.set(var1[var6], var2.get(var6));
      }

      return var4;
   }

   private Vector3f getVector(String var1) {
      Vector3f var2 = new Vector3f();
      String[] var3 = var1.split(",");
      var2.x = Float.parseFloat(var3[0]);
      var2.y = Float.parseFloat(var3[1]);
      var2.z = Float.parseFloat(var3[2]);
      return var2;
   }

   private Quaternion getQuaternion(String var1) {
      Quaternion var2 = new Quaternion();
      String[] var3 = var1.split(",");
      var2.x = Float.parseFloat(var3[0]);
      var2.y = Float.parseFloat(var3[1]);
      var2.z = Float.parseFloat(var3[2]);
      var2.w = Float.parseFloat(var3[3]);
      return var2;
   }

   private Matrix4f getMatrix(String var1, String var2, String var3, String var4) {
      Matrix4f var5 = new Matrix4f();
      boolean var6 = false;
      String[] var7 = var1.split(",");
      var5.m00 = Float.parseFloat(var7[0]);
      var5.m01 = Float.parseFloat(var7[1]);
      var5.m02 = Float.parseFloat(var7[2]);
      var5.m03 = Float.parseFloat(var7[3]);
      var7 = var2.split(",");
      var5.m10 = Float.parseFloat(var7[0]);
      var5.m11 = Float.parseFloat(var7[1]);
      var5.m12 = Float.parseFloat(var7[2]);
      var5.m13 = Float.parseFloat(var7[3]);
      var7 = var3.split(",");
      var5.m20 = Float.parseFloat(var7[0]);
      var5.m21 = Float.parseFloat(var7[1]);
      var5.m22 = Float.parseFloat(var7[2]);
      var5.m23 = Float.parseFloat(var7[3]);
      var7 = var4.split(",");
      var5.m30 = Float.parseFloat(var7[0]);
      var5.m31 = Float.parseFloat(var7[1]);
      var5.m32 = Float.parseFloat(var7[2]);
      var5.m33 = Float.parseFloat(var7[3]);
      return var5;
   }

   public static enum LoadMode {
      Version,
      ModelName,
      VertexStrideElementCount,
      VertexStrideSize,
      VertexStrideData,
      VertexCount,
      VertexBuffer,
      NumberOfFaces,
      FaceData,
      NumberOfBones,
      SkeletonHierarchy,
      BindPose,
      InvBindPose,
      SkinOffsetMatrices,
      NumberOfAnims,
      Anim;
   }
}
