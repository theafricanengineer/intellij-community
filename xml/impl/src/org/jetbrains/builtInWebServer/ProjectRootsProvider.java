package org.jetbrains.builtInWebServer;

import com.intellij.openapi.extensions.ExtensionPointName;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.PairFunction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ProjectRootsProvider {
  static final ExtensionPointName<ProjectRootsProvider> EP_NAME = ExtensionPointName.create("org.jetbrains.webServerProjectRootsProvider");

  @Nullable
  /**
   * @return pair of child and root or null if cannot resolve
   */
  public abstract Pair<VirtualFile, Pair<VirtualFile, String>> resolve(@NotNull String path, @NotNull Project project);

  @Nullable
  public abstract Pair<VirtualFile, String> getRoot(@NotNull VirtualFile file, @NotNull Project project);

  public boolean isClearCacheOnFileContentChanged(@NotNull VirtualFile file) {
    return false;
  }

  public abstract static class PrefixlessProjectRootsProvider extends ProjectRootsProvider {
    @Nullable
    @Override
    public final Pair<VirtualFile, Pair<VirtualFile, String>> resolve(@NotNull String path, @NotNull Project project) {
      return resolve(path, project, PathToFileManager.getInstance(project).getResolver(path));
    }

    @Nullable
    public abstract Pair<VirtualFile, Pair<VirtualFile, String>> resolve(@NotNull String path, @NotNull Project project, @NotNull PairFunction<String, VirtualFile, VirtualFile> resolver);
  }
}