package com.psikonutrisense.app.data.repository;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0018\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J$\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\tH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u000b\u0010\fJ$\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\b2\u0006\u0010\u000f\u001a\u00020\u000eH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0010\u0010\u0011J$\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\b2\u0006\u0010\u000f\u001a\u00020\u0013H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0014\u0010\u0015J$\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\b2\u0006\u0010\u0018\u001a\u00020\u0017H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0019\u0010\u001aJ$\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\b2\u0006\u0010\u001d\u001a\u00020\u001eH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001f\u0010 J$\u0010!\u001a\b\u0012\u0004\u0012\u00020\u001c0\b2\u0006\u0010\u001d\u001a\u00020\u001eH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\"\u0010 J$\u0010#\u001a\b\u0012\u0004\u0012\u00020\u001c0\b2\u0006\u0010\u001d\u001a\u00020\u001eH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b$\u0010 J$\u0010%\u001a\b\u0012\u0004\u0012\u00020\u001c0\b2\u0006\u0010\u001d\u001a\u00020\u001eH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b&\u0010 J\u0014\u0010\'\u001a\u00020(2\n\u0010)\u001a\u0006\u0012\u0002\b\u00030*H\u0002J\u000e\u0010+\u001a\u00020(H\u0082@\u00a2\u0006\u0002\u0010,J\"\u0010-\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0.0\bH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b/\u0010,J.\u00100\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0.0\b2\n\b\u0002\u00101\u001a\u0004\u0018\u00010\u001eH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b2\u00103J.\u00104\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130.0\b2\n\b\u0002\u00101\u001a\u0004\u0018\u00010\u001eH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b5\u00103J\"\u00106\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170.0\bH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b7\u0010,J\"\u00108\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002090.0\bH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b:\u0010,J\f\u0010;\u001a\b\u0012\u0004\u0012\u00020=0<J\u000e\u0010>\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010(0<J\u000e\u0010?\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010(0<J\u000e\u0010@\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010(0<J\u000e\u0010A\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010(0<J6\u0010B\u001a\b\u0012\u0004\u0012\u00020C0\b2\u0006\u0010D\u001a\u00020(2\u0006\u0010E\u001a\u00020(2\b\b\u0002\u0010F\u001a\u00020=H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\bG\u0010HJ\u000e\u0010I\u001a\u00020\u001cH\u0086@\u00a2\u0006\u0002\u0010,J<\u0010J\u001a\b\u0012\u0004\u0012\u00020C0\b2\u0006\u0010K\u001a\u00020(2\u0006\u0010D\u001a\u00020(2\u0006\u0010E\u001a\u00020(2\u0006\u0010L\u001a\u00020(H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\bM\u0010NJ,\u0010O\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\n\u001a\u00020\tH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\bP\u0010QJ,\u0010R\u001a\b\u0012\u0004\u0012\u00020\u000e0\b2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u000f\u001a\u00020\u000eH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\bS\u0010TJ,\u0010U\u001a\b\u0012\u0004\u0012\u00020\u00130\b2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u000f\u001a\u00020\u0013H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\bV\u0010WJ,\u0010X\u001a\b\u0012\u0004\u0012\u00020\u00170\b2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u0018\u001a\u00020\u0017H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\bY\u0010ZR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006["}, d2 = {"Lcom/psikonutrisense/app/data/repository/PsikonutrisenseRepository;", "", "api", "Lcom/psikonutrisense/app/data/remote/PsikonutrisenseApi;", "sessionManager", "Lcom/psikonutrisense/app/data/local/SessionManager;", "(Lcom/psikonutrisense/app/data/remote/PsikonutrisenseApi;Lcom/psikonutrisense/app/data/local/SessionManager;)V", "createChild", "Lkotlin/Result;", "Lcom/psikonutrisense/app/data/model/Child;", "child", "createChild-gIAlu-s", "(Lcom/psikonutrisense/app/data/model/Child;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createGrowthRecord", "Lcom/psikonutrisense/app/data/model/GrowthRecord;", "record", "createGrowthRecord-gIAlu-s", "(Lcom/psikonutrisense/app/data/model/GrowthRecord;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createImmunization", "Lcom/psikonutrisense/app/data/model/VaccinationRecord;", "createImmunization-gIAlu-s", "(Lcom/psikonutrisense/app/data/model/VaccinationRecord;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createMother", "Lcom/psikonutrisense/app/data/model/Mother;", "mother", "createMother-gIAlu-s", "(Lcom/psikonutrisense/app/data/model/Mother;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteChild", "", "id", "", "deleteChild-gIAlu-s", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteGrowthRecord", "deleteGrowthRecord-gIAlu-s", "deleteImmunization", "deleteImmunization-gIAlu-s", "deleteMother", "deleteMother-gIAlu-s", "extractErrorMessage", "", "response", "Lretrofit2/Response;", "getAuthHeader", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getChildren", "", "getChildren-IoAF18A", "getGrowthRecords", "childId", "getGrowthRecords-gIAlu-s", "(Ljava/lang/Integer;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getImmunizations", "getImmunizations-gIAlu-s", "getMothers", "getMothers-IoAF18A", "getRecipes", "Lcom/psikonutrisense/app/data/model/LocalRecipe;", "getRecipes-IoAF18A", "getRememberMe", "Lkotlinx/coroutines/flow/Flow;", "", "getToken", "getUserEmail", "getUserName", "getUserRole", "login", "Lcom/psikonutrisense/app/data/model/AuthResponse;", "email", "pass", "rememberMe", "login-BWLJW6A", "(Ljava/lang/String;Ljava/lang/String;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "logout", "register", "name", "role", "register-yxL6bBk", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateChild", "updateChild-0E7RQCE", "(ILcom/psikonutrisense/app/data/model/Child;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateGrowthRecord", "updateGrowthRecord-0E7RQCE", "(ILcom/psikonutrisense/app/data/model/GrowthRecord;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateImmunization", "updateImmunization-0E7RQCE", "(ILcom/psikonutrisense/app/data/model/VaccinationRecord;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateMother", "updateMother-0E7RQCE", "(ILcom/psikonutrisense/app/data/model/Mother;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class PsikonutrisenseRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.psikonutrisense.app.data.remote.PsikonutrisenseApi api = null;
    @org.jetbrains.annotations.NotNull()
    private final com.psikonutrisense.app.data.local.SessionManager sessionManager = null;
    
    @javax.inject.Inject()
    public PsikonutrisenseRepository(@org.jetbrains.annotations.NotNull()
    com.psikonutrisense.app.data.remote.PsikonutrisenseApi api, @org.jetbrains.annotations.NotNull()
    com.psikonutrisense.app.data.local.SessionManager sessionManager) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.String> getToken() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.String> getUserName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.String> getUserEmail() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.String> getUserRole() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Boolean> getRememberMe() {
        return null;
    }
    
    private final java.lang.Object getAuthHeader(kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    private final java.lang.String extractErrorMessage(retrofit2.Response<?> response) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object logout(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}