package com.psikonutrisense.app.di;

import com.psikonutrisense.app.data.local.SessionManager;
import com.psikonutrisense.app.data.remote.PsikonutrisenseApi;
import com.psikonutrisense.app.data.repository.PsikonutrisenseRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class AppModule_ProvidePsikonutrisenseRepositoryFactory implements Factory<PsikonutrisenseRepository> {
  private final Provider<PsikonutrisenseApi> apiProvider;

  private final Provider<SessionManager> sessionManagerProvider;

  public AppModule_ProvidePsikonutrisenseRepositoryFactory(Provider<PsikonutrisenseApi> apiProvider,
      Provider<SessionManager> sessionManagerProvider) {
    this.apiProvider = apiProvider;
    this.sessionManagerProvider = sessionManagerProvider;
  }

  @Override
  public PsikonutrisenseRepository get() {
    return providePsikonutrisenseRepository(apiProvider.get(), sessionManagerProvider.get());
  }

  public static AppModule_ProvidePsikonutrisenseRepositoryFactory create(
      Provider<PsikonutrisenseApi> apiProvider, Provider<SessionManager> sessionManagerProvider) {
    return new AppModule_ProvidePsikonutrisenseRepositoryFactory(apiProvider, sessionManagerProvider);
  }

  public static PsikonutrisenseRepository providePsikonutrisenseRepository(PsikonutrisenseApi api,
      SessionManager sessionManager) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.providePsikonutrisenseRepository(api, sessionManager));
  }
}
