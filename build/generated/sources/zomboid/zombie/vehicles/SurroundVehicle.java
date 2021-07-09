package zombie.vehicles;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.joml.Vector2f;
import org.joml.Vector3f;
import zombie.VirtualZombieManager;
import zombie.characters.IsoGameCharacter;
import zombie.characters.IsoZombie;
import zombie.iso.IsoUtils;
import zombie.popman.ObjectPool;
import zombie.scripting.objects.VehicleScript;
import zombie.util.Type;

public final class SurroundVehicle {
   private static final ObjectPool s_positionPool = new ObjectPool(() -> {
      return new SurroundVehicle.Position();
   });
   private static final Vector3f s_tempVector3f = new Vector3f();
   private final BaseVehicle m_vehicle;
   public float x1;
   public float y1;
   public float x2;
   public float y2;
   public float x3;
   public float y3;
   public float x4;
   public float y4;
   private boolean m_bMoved = false;
   private final ArrayList m_positions = new ArrayList();
   private long m_updateMS = 0L;

   public SurroundVehicle(BaseVehicle var1) {
      Objects.requireNonNull(var1);
      this.m_vehicle = var1;
   }

   private void calcPositionsLocal() {
      s_positionPool.release((List)this.m_positions);
      this.m_positions.clear();
      VehicleScript var1 = this.m_vehicle.getScript();
      if (var1 != null) {
         Vector3f var2 = var1.getExtents();
         Vector3f var3 = var1.getCenterOfMassOffset();
         float var4 = var2.x;
         float var5 = var2.z;
         float var6 = 0.005F;
         float var7 = BaseVehicle.PLUS_RADIUS + var6;
         float var8 = var3.x - var4 / 2.0F - var7;
         float var9 = var3.z - var5 / 2.0F - var7;
         float var10 = var3.x + var4 / 2.0F + var7;
         float var11 = var3.z + var5 / 2.0F + var7;
         this.addPositions(var8, var3.z - var5 / 2.0F, var8, var3.z + var5 / 2.0F);
         this.addPositions(var10, var3.z - var5 / 2.0F, var10, var3.z + var5 / 2.0F);
         this.addPositions(var8, var9, var10, var9);
         this.addPositions(var8, var11, var10, var11);
      }
   }

   private void addPositions(float var1, float var2, float var3, float var4) {
      Vector3f var5 = this.m_vehicle.getPassengerLocalPos(0, s_tempVector3f);
      if (var5 != null) {
         float var6 = 0.3F;
         float var7;
         float var8;
         float var9;
         if (var1 == var3) {
            var7 = var1;
            var8 = var5.z;

            for(var9 = var8; var9 >= var2 + var6; var9 -= var6 * 2.0F) {
               this.addPosition(var7, var9);
            }

            for(var9 = var8 + var6 * 2.0F; var9 < var4 - var6; var9 += var6 * 2.0F) {
               this.addPosition(var7, var9);
            }
         } else {
            var7 = 0.0F;
            var8 = var2;

            for(var9 = var7; var9 >= var1 + var6; var9 -= var6 * 2.0F) {
               this.addPosition(var9, var8);
            }

            for(var9 = var7 + var6 * 2.0F; var9 < var3 - var6; var9 += var6 * 2.0F) {
               this.addPosition(var9, var8);
            }
         }

      }
   }

   private SurroundVehicle.Position addPosition(float var1, float var2) {
      SurroundVehicle.Position var3 = (SurroundVehicle.Position)s_positionPool.alloc();
      var3.posLocal.set(var1, var2);
      this.m_positions.add(var3);
      return var3;
   }

   private void calcPositionsWorld() {
      for(int var1 = 0; var1 < this.m_positions.size(); ++var1) {
         SurroundVehicle.Position var2 = (SurroundVehicle.Position)this.m_positions.get(var1);
         this.m_vehicle.getWorldPos(var2.posLocal.x, 0.0F, var2.posLocal.y, var2.posWorld);
      }

   }

   private SurroundVehicle.Position getClosestPositionFor(IsoZombie var1) {
      if (var1 != null && var1.getTarget() != null) {
         float var2 = Float.MAX_VALUE;
         SurroundVehicle.Position var3 = null;

         for(int var4 = 0; var4 < this.m_positions.size(); ++var4) {
            SurroundVehicle.Position var5 = (SurroundVehicle.Position)this.m_positions.get(var4);
            if (!var5.bBlocked) {
               float var6 = IsoUtils.DistanceToSquared(var1.x, var1.y, var5.posWorld.x, var5.posWorld.y);
               float var7;
               if (var5.isOccupied()) {
                  var7 = IsoUtils.DistanceToSquared(var5.zombie.x, var5.zombie.y, var5.posWorld.x, var5.posWorld.y);
                  if (var7 < var6) {
                     continue;
                  }
               }

               var7 = IsoUtils.DistanceToSquared(var1.getTarget().x, var1.getTarget().y, var5.posWorld.x, var5.posWorld.y);
               if (var7 < var2) {
                  var2 = var7;
                  var3 = var5;
               }
            }
         }

         return var3;
      } else {
         return null;
      }
   }

   public Vector2f getPositionForZombie(IsoZombie var1, Vector2f var2) {
      if ((!var1.isOnFloor() || var1.isCanWalk()) && (int)var1.getZ() == (int)this.m_vehicle.getZ()) {
         float var3 = IsoUtils.DistanceToSquared(var1.x, var1.y, this.m_vehicle.x, this.m_vehicle.y);
         if (var3 > 100.0F) {
            return var2.set(this.m_vehicle.x, this.m_vehicle.y);
         } else {
            if (this.checkPosition()) {
               this.m_bMoved = true;
            }

            for(int var4 = 0; var4 < this.m_positions.size(); ++var4) {
               SurroundVehicle.Position var5 = (SurroundVehicle.Position)this.m_positions.get(var4);
               if (var5.bBlocked) {
                  var5.zombie = null;
               }

               if (var5.zombie == var1) {
                  return var2.set(var5.posWorld.x, var5.posWorld.y);
               }
            }

            SurroundVehicle.Position var6 = this.getClosestPositionFor(var1);
            if (var6 == null) {
               return null;
            } else {
               var6.zombie = var1;
               var6.targetX = var1.getTarget().x;
               var6.targetY = var1.getTarget().y;
               return var2.set(var6.posWorld.x, var6.posWorld.y);
            }
         }
      } else {
         return var2.set(this.m_vehicle.x, this.m_vehicle.y);
      }
   }

   private boolean checkPosition() {
      if (this.m_vehicle.getScript() == null) {
         return false;
      } else {
         if (this.m_positions.isEmpty()) {
            this.calcPositionsLocal();
            this.x1 = -1.0F;
         }

         PolygonalMap2.VehiclePoly var1 = this.m_vehicle.getPoly();
         if (this.x1 == var1.x1 && this.x2 == var1.x2 && this.x3 == var1.x3 && this.x4 == var1.x4 && this.y1 == var1.y1 && this.y2 == var1.y2 && this.y3 == var1.y3 && this.y4 == var1.y4) {
            return false;
         } else {
            this.x1 = var1.x1;
            this.x2 = var1.x2;
            this.x3 = var1.x3;
            this.x4 = var1.x4;
            this.y1 = var1.y1;
            this.y2 = var1.y2;
            this.y3 = var1.y3;
            this.y4 = var1.y4;
            this.calcPositionsWorld();
            return true;
         }
      }
   }

   private boolean hasOccupiedPositions() {
      for(int var1 = 0; var1 < this.m_positions.size(); ++var1) {
         SurroundVehicle.Position var2 = (SurroundVehicle.Position)this.m_positions.get(var1);
         if (var2.zombie != null) {
            return true;
         }
      }

      return false;
   }

   public void update() {
      if (this.hasOccupiedPositions() && this.checkPosition()) {
         this.m_bMoved = true;
      }

      long var1 = System.currentTimeMillis();
      if (var1 - this.m_updateMS >= 1000L) {
         this.m_updateMS = var1;
         int var3;
         SurroundVehicle.Position var4;
         if (this.m_bMoved) {
            this.m_bMoved = false;

            for(var3 = 0; var3 < this.m_positions.size(); ++var3) {
               var4 = (SurroundVehicle.Position)this.m_positions.get(var3);
               var4.zombie = null;
            }
         }

         for(var3 = 0; var3 < this.m_positions.size(); ++var3) {
            var4 = (SurroundVehicle.Position)this.m_positions.get(var3);
            var4.checkBlocked(this.m_vehicle);
            if (var4.zombie != null) {
               float var5 = IsoUtils.DistanceToSquared(var4.zombie.x, var4.zombie.y, this.m_vehicle.x, this.m_vehicle.y);
               if (var5 > 100.0F) {
                  var4.zombie = null;
               } else {
                  IsoGameCharacter var6 = (IsoGameCharacter)Type.tryCastTo(var4.zombie.getTarget(), IsoGameCharacter.class);
                  if (!var4.zombie.isDead() && !VirtualZombieManager.instance.isReused(var4.zombie) && !var4.zombie.isOnFloor() && var6 != null && this.m_vehicle.getSeat(var6) != -1) {
                     if (IsoUtils.DistanceToSquared(var4.targetX, var4.targetY, var6.x, var6.y) > 0.1F) {
                        var4.zombie = null;
                     }
                  } else {
                     var4.zombie = null;
                  }
               }
            }
         }

      }
   }

   public void render() {
      if (this.hasOccupiedPositions()) {
         for(int var1 = 0; var1 < this.m_positions.size(); ++var1) {
            SurroundVehicle.Position var2 = (SurroundVehicle.Position)this.m_positions.get(var1);
            Vector3f var3 = var2.posWorld;
            float var4 = 1.0F;
            float var5 = 1.0F;
            float var6 = 1.0F;
            if (var2.isOccupied()) {
               var6 = 0.0F;
               var4 = 0.0F;
            } else if (var2.bBlocked) {
               var6 = 0.0F;
               var5 = 0.0F;
            }

            this.m_vehicle.getController().drawCircle(var3.x, var3.y, 0.3F, var4, var5, var6, 1.0F);
         }

      }
   }

   public void reset() {
      s_positionPool.release((List)this.m_positions);
      this.m_positions.clear();
   }

   private static final class Position {
      final Vector2f posLocal;
      final Vector3f posWorld;
      IsoZombie zombie;
      float targetX;
      float targetY;
      boolean bBlocked;

      private Position() {
         this.posLocal = new Vector2f();
         this.posWorld = new Vector3f();
      }

      boolean isOccupied() {
         return this.zombie != null;
      }

      void checkBlocked(BaseVehicle var1) {
         this.bBlocked = PolygonalMap2.instance.lineClearCollide(this.posWorld.x, this.posWorld.y, var1.x, var1.y, (int)var1.z, var1);
      }

      // $FF: synthetic method
      Position(Object var1) {
         this();
      }
   }
}
