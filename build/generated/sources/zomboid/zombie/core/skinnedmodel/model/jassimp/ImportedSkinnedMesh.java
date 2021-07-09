package zombie.core.skinnedmodel.model.jassimp;

import jassimp.AiBone;
import jassimp.AiBoneWeight;
import jassimp.AiMesh;
import java.util.List;
import zombie.core.skinnedmodel.model.VertexBufferObject;

public final class ImportedSkinnedMesh {
   final ImportedSkeleton skeleton;
   String name;
   VertexBufferObject.VertexArray vertices = null;
   int[] elements = null;

   public ImportedSkinnedMesh(ImportedSkeleton var1, AiMesh var2) {
      this.skeleton = var1;
      this.processAiScene(var2);
   }

   private void processAiScene(AiMesh var1) {
      this.name = var1.getName();
      int var2 = var1.getNumVertices();
      int var4 = var2 * 4;
      int[] var5 = new int[var4];
      float[] var6 = new float[var4];

      for(int var7 = 0; var7 < var4; ++var7) {
         var6[var7] = 0.0F;
      }

      List var18 = var1.getBones();
      int var8 = var18.size();

      int var9;
      int var12;
      for(var9 = 0; var9 < var8; ++var9) {
         AiBone var10 = (AiBone)var18.get(var9);
         String var11 = var10.getName();
         var12 = (Integer)this.skeleton.boneIndices.get(var11);
         List var13 = var10.getBoneWeights();

         for(int var14 = 0; var14 < var10.getNumWeights(); ++var14) {
            AiBoneWeight var15 = (AiBoneWeight)var13.get(var14);
            int var16 = var15.getVertexId() * 4;

            for(int var17 = 0; var17 < 4; ++var17) {
               if (var6[var16 + var17] == 0.0F) {
                  var6[var16 + var17] = var15.getWeight();
                  var5[var16 + var17] = var12;
                  break;
               }
            }
         }
      }

      var9 = getNumUVs(var1);
      VertexBufferObject.VertexFormat var19 = new VertexBufferObject.VertexFormat(5 + var9);
      var19.setElement(0, VertexBufferObject.VertexType.VertexArray, 12);
      var19.setElement(1, VertexBufferObject.VertexType.NormalArray, 12);
      var19.setElement(2, VertexBufferObject.VertexType.TangentArray, 12);
      var19.setElement(3, VertexBufferObject.VertexType.BlendWeightArray, 16);
      var19.setElement(4, VertexBufferObject.VertexType.BlendIndexArray, 16);

      int var20;
      for(var20 = 0; var20 < var9; ++var20) {
         var19.setElement(5 + var20, VertexBufferObject.VertexType.TextureCoordArray, 8);
      }

      var19.calculate();
      this.vertices = new VertexBufferObject.VertexArray(var19, var2);

      for(var20 = 0; var20 < var2; ++var20) {
         this.vertices.setElement(var20, 0, var1.getPositionX(var20), var1.getPositionY(var20), var1.getPositionZ(var20));
         if (var1.hasNormals()) {
            this.vertices.setElement(var20, 1, var1.getNormalX(var20), var1.getNormalY(var20), var1.getNormalZ(var20));
         } else {
            this.vertices.setElement(var20, 1, 0.0F, 1.0F, 0.0F);
         }

         if (var1.hasTangentsAndBitangents()) {
            this.vertices.setElement(var20, 2, var1.getTangentX(var20), var1.getTangentY(var20), var1.getTangentZ(var20));
         } else {
            this.vertices.setElement(var20, 2, 0.0F, 0.0F, 1.0F);
         }

         this.vertices.setElement(var20, 3, var6[var20 * 4], var6[var20 * 4 + 1], var6[var20 * 4 + 2], var6[var20 * 4 + 3]);
         this.vertices.setElement(var20, 4, (float)var5[var20 * 4], (float)var5[var20 * 4 + 1], (float)var5[var20 * 4 + 2], (float)var5[var20 * 4 + 3]);
         if (var9 > 0) {
            var12 = 0;

            for(int var21 = 0; var21 < 8; ++var21) {
               if (var1.hasTexCoords(var21)) {
                  this.vertices.setElement(var20, 5 + var12, var1.getTexCoordU(var20, var21), 1.0F - var1.getTexCoordV(var20, var21));
                  ++var12;
               }
            }
         }
      }

      var20 = var1.getNumFaces();
      this.elements = new int[var20 * 3];

      for(var12 = 0; var12 < var20; ++var12) {
         this.elements[var12 * 3 + 2] = var1.getFaceVertex(var12, 0);
         this.elements[var12 * 3 + 1] = var1.getFaceVertex(var12, 1);
         this.elements[var12 * 3 + 0] = var1.getFaceVertex(var12, 2);
      }

   }

   private static int getNumUVs(AiMesh var0) {
      int var1 = 0;

      for(int var2 = 0; var2 < 8; ++var2) {
         if (var0.hasTexCoords(var2)) {
            ++var1;
         }
      }

      return var1;
   }
}
