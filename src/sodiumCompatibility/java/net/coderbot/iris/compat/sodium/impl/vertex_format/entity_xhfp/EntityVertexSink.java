package net.coderbot.iris.compat.sodium.impl.vertex_format.entity_xhfp;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexFormat;
import me.jellysquid.mods.sodium.client.model.vertex.VertexSink;
import me.jellysquid.mods.sodium.client.model.vertex.formats.quad.QuadVertexSink;
import me.jellysquid.mods.sodium.client.util.math.Matrix4fExtended;
import me.jellysquid.mods.sodium.client.util.math.MatrixUtil;
import net.coderbot.iris.vertices.IrisVertexFormats;

import java.nio.ByteBuffer;

public interface EntityVertexSink extends VertexSink, QuadVertexSink {
    VertexFormat VERTEX_FORMAT = IrisVertexFormats.ENTITY;

    /**
     * Writes a quad vertex to this sink.
     *
     * @param x The x-position of the vertex
     * @param y The y-position of the vertex
     * @param z The z-position of the vertex
     * @param color The ABGR-packed color of the vertex
     * @param u The u-texture of the vertex
     * @param v The y-texture of the vertex
     * @param light The packed light-map coordinates of the vertex
     * @param overlay The packed overlay-map coordinates of the vertex
     * @param normal The 3-byte packed normal vector of the vertex
     */
    void writeQuad(float x, float y, float z, int color, float u, float v, int light, int overlay, int normal);

    /**
     * Writes a quad vertex to the sink, transformed by the given matrices.
     *
     * @param pose The matrices to transform the vertex's position and normal vectors by
     */
    default void writeQuad(PoseStack.Pose pose, float x, float y, float z, int color, float u, float v, int light, int overlay, int normal) {
        Matrix4fExtended modelMatrix = MatrixUtil.getExtendedMatrix(pose.pose());

        float x2 = modelMatrix.transformVecX(x, y, z);
        float y2 = modelMatrix.transformVecY(x, y, z);
        float z2 = modelMatrix.transformVecZ(x, y, z);

        int norm = MatrixUtil.transformPackedNormal(normal, pose.normal());

        this.writeQuad(x2, y2, z2, color, u, v, light, overlay, norm);
    }
}
