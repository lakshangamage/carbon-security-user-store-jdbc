/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wso2.carbon.security.connector.osgi;

import org.testng.annotations.Test;
import org.wso2.carbon.security.caas.user.core.bean.Action;
import org.wso2.carbon.security.caas.user.core.bean.Group;
import org.wso2.carbon.security.caas.user.core.bean.Permission;
import org.wso2.carbon.security.caas.user.core.bean.Resource;
import org.wso2.carbon.security.caas.user.core.bean.Role;
import org.wso2.carbon.security.caas.user.core.bean.User;
import org.wso2.carbon.security.caas.user.core.exception.AuthorizationStoreException;
import org.wso2.carbon.security.caas.user.core.exception.GroupNotFoundException;
import org.wso2.carbon.security.caas.user.core.exception.IdentityStoreException;
import org.wso2.carbon.security.caas.user.core.exception.PermissionNotFoundException;
import org.wso2.carbon.security.caas.user.core.exception.RoleNotFoundException;
import org.wso2.carbon.security.caas.user.core.exception.UserNotFoundException;
import org.wso2.carbon.security.caas.user.core.store.AuthorizationStore;
import org.wso2.carbon.security.caas.user.core.store.IdentityStore;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * Authorization store connector related test cases.
 */
public class JDBCAuthorizationConnectorTests extends JDBCConnectorTests {

    @Test(priority = 1)
    public void testIsUserAuthorizedValid() throws AuthorizationStoreException,
            IdentityStoreException {

        AuthorizationStore authorizationStore = realmService.getAuthorizationStore();
        assertTrue(authorizationStore.isUserAuthorized(DEFAULT_USER_ID, DEFAULT_PERMISSION, DEFAULT_IDENTITY_STORE));
    }

    @Test(priority = 2)
    public void testIsGroupAuthorizedValid() throws AuthorizationStoreException {

        AuthorizationStore authorizationStore = realmService.getAuthorizationStore();
        authorizationStore.isGroupAuthorized(DEFAULT_GROUP_ID, DEFAULT_IDENTITY_STORE, DEFAULT_PERMISSION);
    }

    @Test(priority = 3)
    public void testAddNewRoleValid() throws AuthorizationStoreException {

        List<Permission> permissions = new ArrayList<>();
        permissions.add(new Permission.PermissionBuilder(DEFAULT_RESOURCE, ACTION_ADD, DEFAULT_PERMISSION_ID,
                DEFAULT_AUTHORIZATION_STORE).build());

        AuthorizationStore authorizationStore = realmService.getAuthorizationStore();
        Role role = authorizationStore.addRole("test", permissions, DEFAULT_AUTHORIZATION_STORE);

        assertNotNull(role.getRoleId());
    }

    @Test(priority = 3)
    public void testAddNewRoleToPrimaryValid() throws AuthorizationStoreException {

        List<Permission> permissions = new ArrayList<>();
        permissions.add(new Permission.PermissionBuilder(DEFAULT_RESOURCE, ACTION_ADD, DEFAULT_PERMISSION_ID,
                DEFAULT_AUTHORIZATION_STORE).build());

        AuthorizationStore authorizationStore = realmService.getAuthorizationStore();
        Role role = authorizationStore.addRole("test1", permissions);

        assertNotNull(role.getRoleId());
    }

    @Test(priority = 3)
    public void testAddNewActionValid() throws AuthorizationStoreException {

        AuthorizationStore authorizationStore = realmService.getAuthorizationStore();
        Action action = authorizationStore.addAction("reg", "test-action", DEFAULT_AUTHORIZATION_STORE);

        assertNotNull(action);
    }

    @Test(priority = 3)
    public void testAddNewActionToPrimaryValid() throws AuthorizationStoreException {

        AuthorizationStore authorizationStore = realmService.getAuthorizationStore();
        Action action = authorizationStore.addAction("reg", "test-action-1");

        assertNotNull(action);
    }

    @Test(priority = 3)
    public void testAddNewResourceValid() throws AuthorizationStoreException {

        AuthorizationStore authorizationStore = realmService.getAuthorizationStore();
        Resource resource = authorizationStore.addResource("reg", "root/resource/test-resource",
                DEFAULT_AUTHORIZATION_STORE, DEFAULT_USER_ID, DEFAULT_IDENTITY_STORE);

        assertNotNull(resource);
    }

    @Test(priority = 3)
    public void testAddNewResourceToPrimaryValid() throws AuthorizationStoreException {

        AuthorizationStore authorizationStore = realmService.getAuthorizationStore();
        Resource resource = authorizationStore.addResource("reg", "root/resource/test-resource-1", DEFAULT_USER_ID,
                DEFAULT_IDENTITY_STORE);

        assertNotNull(resource);
    }

    @Test(priority = 4)
    public void testAddNewPermissionValid() throws AuthorizationStoreException {

        AuthorizationStore authorizationStore = realmService.getAuthorizationStore();
        Permission permission = authorizationStore.addPermission(new Resource("reg", "root/resource/test-resource",
                DEFAULT_USER_ID, DEFAULT_IDENTITY_STORE), new Action("reg", "test-action"));

        assertNotNull(permission.getPermissionId());
    }

    @Test(priority = 4)
    public void testAddNewPermissionToPrimaryValid() throws AuthorizationStoreException {

        AuthorizationStore authorizationStore = realmService.getAuthorizationStore();
        Permission permission = authorizationStore.addPermission(new Resource("reg", "root/resource/test-resource-1",
                        DEFAULT_USER_ID, DEFAULT_IDENTITY_STORE), new Action("reg", "test-action-1"),
                DEFAULT_AUTHORIZATION_STORE);

        assertNotNull(permission.getPermissionId());
    }

    @Test(priority = 5)
    public void testIsUserInRoleValid() throws AuthorizationStoreException {

        AuthorizationStore authorizationStore = realmService.getAuthorizationStore();
        assertTrue(authorizationStore.isUserInRole(DEFAULT_USER_ID, DEFAULT_IDENTITY_STORE, DEFAULT_ROLE));
    }

    @Test(priority = 6)
    public void testIsGroupInRoleValid() throws AuthorizationStoreException {

        AuthorizationStore authorizationStore = realmService.getAuthorizationStore();
        assertTrue(authorizationStore.isGroupInRole(DEFAULT_GROUP_ID, DEFAULT_IDENTITY_STORE, DEFAULT_ROLE));
    }

    @Test(priority = 7)
    public void testGetRoleValid() throws RoleNotFoundException, AuthorizationStoreException {

        AuthorizationStore authorizationStore = realmService.getAuthorizationStore();
        assertNotNull(authorizationStore.getRole(DEFAULT_ROLE));
    }

    @Test
    public void testListRolesValid() throws AuthorizationStoreException {

        AuthorizationStore authorizationStore = realmService.getAuthorizationStore();
        List<Role> roles = authorizationStore.listRoles("*", 0, -1);

        assertFalse(roles.isEmpty());
    }

    @Test(priority = 8)
    public void testGetPermissionValid() throws PermissionNotFoundException, AuthorizationStoreException {

        AuthorizationStore authorizationStore = realmService.getAuthorizationStore();
        assertNotNull(authorizationStore.getPermission(DEFAULT_PERMISSION.getResource().getResourceString(),
                DEFAULT_PERMISSION.getAction().getActionString()));
    }

    @Test
    public void testListPermissionsValid() throws AuthorizationStoreException {

        AuthorizationStore authorizationStore = realmService.getAuthorizationStore();
        List<Permission> permissions = authorizationStore.listPermissions("*", "*", 0, -1);

        assertFalse(permissions.isEmpty());
    }

    @Test
    public void testGetResourcesValid() throws AuthorizationStoreException {

        AuthorizationStore authorizationStore = realmService.getAuthorizationStore();
        List<Resource> resources = authorizationStore.listResources("*");

        assertFalse(resources.isEmpty());
    }

    @Test
    public void testGetActionsValid() throws AuthorizationStoreException {

        AuthorizationStore authorizationStore = realmService.getAuthorizationStore();
        List<Action> actions = authorizationStore.listActions("*");

        assertFalse(actions.isEmpty());
    }

    @Test(priority = 9)
    public void testGetUsersOfRole() throws AuthorizationStoreException, IdentityStoreException {

        AuthorizationStore authorizationStore = realmService.getAuthorizationStore();
        assertNotNull(authorizationStore.getUsersOfRole(DEFAULT_ROLE_ID, DEFAULT_AUTHORIZATION_STORE));
    }

    @Test(priority = 10)
    public void testGetGroupsOfRole() throws AuthorizationStoreException, IdentityStoreException {

        AuthorizationStore authorizationStore = realmService.getAuthorizationStore();
        assertNotNull(authorizationStore.getGroupsOfRole(DEFAULT_ROLE_ID, DEFAULT_AUTHORIZATION_STORE));
    }

    @Test(priority = 10)
    public void testGetPermissionsForRoleFromResourceValid() throws AuthorizationStoreException {

        AuthorizationStore authorizationStore = realmService.getAuthorizationStore();

        List<Permission> permissions = authorizationStore
                .getPermissionsOfRole(DEFAULT_ROLE_ID, DEFAULT_AUTHORIZATION_STORE, DEFAULT_RESOURCE);

        assertNotNull(permissions);
    }

    @Test(priority = 10)
    public void testGetPermissionsForRoleFromActionValid() throws AuthorizationStoreException {

        AuthorizationStore authorizationStore = realmService.getAuthorizationStore();

        Action action = new Action("reg", "add");

        List<Permission> permissions = authorizationStore
                .getPermissionsOfRole(DEFAULT_ROLE_ID, DEFAULT_AUTHORIZATION_STORE, action);

        assertNotNull(permissions);
    }

    @Test(priority = 11)
    public void testDeletePermission() throws AuthorizationStoreException {

        AuthorizationStore authorizationStore = realmService.getAuthorizationStore();
        authorizationStore.deletePermission(new Permission
                .PermissionBuilder(DEFAULT_RESOURCE, new Action("reg", "action2"), "e890c688135011e6a1483e1d05defe78",
                DEFAULT_AUTHORIZATION_STORE).build());
    }

    @Test(priority = 12)
    public void testDeleteRoleValid() throws AuthorizationStoreException {

        AuthorizationStore authorizationStore = realmService.getAuthorizationStore();
        authorizationStore.deleteRole(new Role.RoleBuilder()
                .setAuthorizationStoreId(DEFAULT_AUTHORIZATION_STORE)
                .setAuthorizationStore(authorizationStore)
                .setRoleId("7f8adbe6134c11e6a1483e1d05defe78")
                .setRoleName("role1")
                .build());
    }

    @Test
    public void testDeleteResourceValid() throws AuthorizationStoreException {

        AuthorizationStore authorizationStore = realmService.getAuthorizationStore();
        authorizationStore.deleteResource(new Resource.ResourceBuilder()
                .setAuthorizationStore(DEFAULT_AUTHORIZATION_STORE)
                .setResourceNamespace("reg")
                .setResourceId("root/resource/delete")
                .setUserId(DEFAULT_USER_ID)
                .setIdentityStoreId(DEFAULT_IDENTITY_STORE)
                .build());
    }

    @Test
    public void testDeleteActionValid() throws AuthorizationStoreException {

        AuthorizationStore authorizationStore = realmService.getAuthorizationStore();
        authorizationStore.deleteAction(new Action.ActionBuilder()
                .setActionNamespace("reg")
                .setAction("action3")
                .setAuthorizationStore(DEFAULT_AUTHORIZATION_STORE)
                .build());
    }

    @Test(priority = 13)
    public void testUpdateRolesInUserPutValid() throws AuthorizationStoreException {

        AuthorizationStore authorizationStore = realmService.getAuthorizationStore();

        List<Role> roles = new ArrayList<>();
        roles.add(new Role.RoleBuilder()
                .setAuthorizationStoreId(DEFAULT_AUTHORIZATION_STORE)
                .setAuthorizationStore(authorizationStore)
                .setRoleId("985b79ecfcdf11e586aa5e5517507c66")
                .setRoleName("admin")
                .build());
        roles.add(new Role.RoleBuilder()
                .setAuthorizationStoreId(DEFAULT_AUTHORIZATION_STORE)
                .setAuthorizationStore(authorizationStore)
                .setRoleId("df813f5e105e11e6a1483e1d05defe78")
                .setRoleName("guest")
                .build());
        roles.add(new Role.RoleBuilder()
                .setAuthorizationStoreId(DEFAULT_AUTHORIZATION_STORE)
                .setAuthorizationStore(authorizationStore)
                .setRoleId("70e2e088105f11e6a1483e1d05defe78")
                .setRoleName("general")
                .build());

        authorizationStore.updateRolesInUser(DEFAULT_USER_ID, DEFAULT_IDENTITY_STORE, roles);
    }

    @Test(priority = 14)
    public void testUpdateRolesInUserPatchValid() throws AuthorizationStoreException {

        AuthorizationStore authorizationStore = realmService.getAuthorizationStore();

        List<Role> roles = new ArrayList<>();
        roles.add(new Role.RoleBuilder()
                .setAuthorizationStoreId(DEFAULT_AUTHORIZATION_STORE)
                .setAuthorizationStore(authorizationStore)
                .setRoleId("985b79ecfcdf11e586aa5e5517507c66")
                .setRoleName("admin")
                .build());
        roles.add(new Role.RoleBuilder()
                .setAuthorizationStoreId(DEFAULT_AUTHORIZATION_STORE)
                .setAuthorizationStore(authorizationStore)
                .setRoleId("df813f5e105e11e6a1483e1d05defe78")
                .setRoleName("guest")
                .build());
        roles.add(new Role.RoleBuilder()
                .setAuthorizationStoreId(DEFAULT_AUTHORIZATION_STORE)
                .setAuthorizationStore(authorizationStore)
                .setRoleId("70e2e088105f11e6a1483e1d05defe78")
                .setRoleName("general")
                .build());

        authorizationStore.updateRolesInUser(DEFAULT_USER_ID, DEFAULT_IDENTITY_STORE, roles, roles);
    }

    @Test(priority = 15)
    public void testUpdateUsersInRolePutValid() throws AuthorizationStoreException {

        AuthorizationStore authorizationStore = realmService.getAuthorizationStore();

        List<User> users = new ArrayList<>();
        users.add(new User.UserBuilder()
                .setUserName("user1")
                .setUserId("b5572242139d11e6a1483e1d05defe78")
                .setTenantDomain("wso2.com")
                .setCredentialStoreId(DEFAULT_CREDENTIAL_STORE)
                .setIdentityStoreId(DEFAULT_IDENTITY_STORE)
                .setIdentityStore(realmService.getIdentityStore())
                .setAuthorizationStore(realmService.getAuthorizationStore())
                .setClaimManager(realmService.getClaimManager())
                .build());

        users.add(new User.UserBuilder()
                .setUserName("user2")
                .setUserId("b5572580139d11e6a1483e1d05defe78")
                .setTenantDomain("wso2.com")
                .setCredentialStoreId(DEFAULT_CREDENTIAL_STORE)
                .setIdentityStoreId(DEFAULT_IDENTITY_STORE)
                .setIdentityStore(realmService.getIdentityStore())
                .setAuthorizationStore(realmService.getAuthorizationStore())
                .setClaimManager(realmService.getClaimManager())
                .build());

        authorizationStore.updateUsersInRole(DEFAULT_ROLE_ID, DEFAULT_AUTHORIZATION_STORE, users);
    }

    @Test(priority = 16)
    public void testUpdateUsersInRolePatchValid() throws AuthorizationStoreException {

        AuthorizationStore authorizationStore = realmService.getAuthorizationStore();

        List<User> users = new ArrayList<>();
        users.add(new User.UserBuilder()
                .setUserName("user1")
                .setUserId("b5572242139d11e6a1483e1d05defe78")
                .setTenantDomain("wso2.com")
                .setCredentialStoreId(DEFAULT_CREDENTIAL_STORE)
                .setIdentityStoreId(DEFAULT_IDENTITY_STORE)
                .setIdentityStore(realmService.getIdentityStore())
                .setAuthorizationStore(realmService.getAuthorizationStore())
                .setClaimManager(realmService.getClaimManager())
                .build());

        users.add(new User.UserBuilder()
                .setUserName("user2")
                .setUserId("b5572580139d11e6a1483e1d05defe78")
                .setTenantDomain("wso2.com")
                .setCredentialStoreId(DEFAULT_CREDENTIAL_STORE)
                .setIdentityStoreId(DEFAULT_IDENTITY_STORE)
                .setIdentityStore(realmService.getIdentityStore())
                .setAuthorizationStore(realmService.getAuthorizationStore())
                .setClaimManager(realmService.getClaimManager())
                .build());

        authorizationStore.updateUsersInRole(DEFAULT_ROLE_ID, DEFAULT_AUTHORIZATION_STORE, users);
    }

    @Test(priority = 17)
    public void testUpdateRolesInGroupPutValid() throws AuthorizationStoreException {

        AuthorizationStore authorizationStore = realmService.getAuthorizationStore();

        List<Role> roles = new ArrayList<>();
        roles.add(new Role.RoleBuilder()
                .setAuthorizationStoreId(DEFAULT_AUTHORIZATION_STORE)
                .setAuthorizationStore(authorizationStore)
                .setRoleId("985b79ecfcdf11e586aa5e5517507c66")
                .setRoleName("admin")
                .build());
        roles.add(new Role.RoleBuilder()
                .setAuthorizationStoreId(DEFAULT_AUTHORIZATION_STORE)
                .setAuthorizationStore(authorizationStore)
                .setRoleId("df813f5e105e11e6a1483e1d05defe78")
                .setRoleName("guest")
                .build());
        roles.add(new Role.RoleBuilder()
                .setAuthorizationStoreId(DEFAULT_AUTHORIZATION_STORE)
                .setAuthorizationStore(authorizationStore)
                .setRoleId("70e2e088105f11e6a1483e1d05defe78")
                .setRoleName("general")
                .build());

        authorizationStore.updateRolesInGroup(DEFAULT_ROLE_ID, DEFAULT_AUTHORIZATION_STORE, roles);
    }

    @Test(priority = 18)
    public void testUpdateRolesInGroupPatchValid() throws AuthorizationStoreException {

        AuthorizationStore authorizationStore = realmService.getAuthorizationStore();

        List<Role> roles = new ArrayList<>();
        roles.add(new Role.RoleBuilder()
                .setAuthorizationStoreId(DEFAULT_AUTHORIZATION_STORE)
                .setAuthorizationStore(authorizationStore)
                .setRoleId("985b79ecfcdf11e586aa5e5517507c66")
                .setRoleName("admin")
                .build());
        roles.add(new Role.RoleBuilder()
                .setAuthorizationStoreId(DEFAULT_AUTHORIZATION_STORE)
                .setAuthorizationStore(authorizationStore)
                .setRoleId("df813f5e105e11e6a1483e1d05defe78")
                .setRoleName("guest")
                .build());
        roles.add(new Role.RoleBuilder()
                .setAuthorizationStoreId(DEFAULT_AUTHORIZATION_STORE)
                .setAuthorizationStore(authorizationStore)
                .setRoleId("70e2e088105f11e6a1483e1d05defe78")
                .setRoleName("general")
                .build());

        authorizationStore.updateRolesInGroup(DEFAULT_ROLE_ID, DEFAULT_AUTHORIZATION_STORE, roles, roles);
    }

    @Test(priority = 19)
    public void testUpdateGroupsInRolePutValid() throws AuthorizationStoreException {

        AuthorizationStore authorizationStore = realmService.getAuthorizationStore();

        List<Group> groups = new ArrayList<>();
        groups.add(new Group.GroupBuilder()
                .setGroupName("is")
                .setGroupId("a422aa98ecf411e59ce95e5517507c66")
                .setTenantDomain("wso2.com")
                .setIdentityStoreId(DEFAULT_IDENTITY_STORE)
                .setIdentityStore(realmService.getIdentityStore())
                .setAuthorizationStore(realmService.getAuthorizationStore())
                .build());
        groups.add(new Group.GroupBuilder()
                .setGroupName("security")
                .setGroupId("16231aee15a711e6a1483e1d05defe78")
                .setTenantDomain("wso2.com")
                .setIdentityStoreId(DEFAULT_IDENTITY_STORE)
                .setIdentityStore(realmService.getIdentityStore())
                .setAuthorizationStore(realmService.getAuthorizationStore())
                .build());

        authorizationStore.updateGroupsInRole(DEFAULT_ROLE_ID, DEFAULT_AUTHORIZATION_STORE, groups);
    }

    @Test(priority = 20)
    public void testUpdateGroupsInRolePatchValid() throws AuthorizationStoreException {

        AuthorizationStore authorizationStore = realmService.getAuthorizationStore();

        List<Group> groups = new ArrayList<>();
        groups.add(new Group.GroupBuilder()
                .setGroupName("is")
                .setGroupId("a422aa98ecf411e59ce95e5517507c66")
                .setTenantDomain("wso2.com")
                .setIdentityStoreId(DEFAULT_IDENTITY_STORE)
                .setIdentityStore(realmService.getIdentityStore())
                .setAuthorizationStore(realmService.getAuthorizationStore())
                .build());
        groups.add(new Group.GroupBuilder()
                .setGroupName("security")
                .setGroupId("16231aee15a711e6a1483e1d05defe78")
                .setTenantDomain("wso2.com")
                .setIdentityStoreId(DEFAULT_IDENTITY_STORE)
                .setIdentityStore(realmService.getIdentityStore())
                .setAuthorizationStore(realmService.getAuthorizationStore())
                .build());

        authorizationStore.updateGroupsInRole(DEFAULT_ROLE_ID, DEFAULT_AUTHORIZATION_STORE, groups, groups);
    }

    @Test(priority = 21)
    public void testUpdatePermissionsInRolePutValid() throws AuthorizationStoreException {

        AuthorizationStore authorizationStore = realmService.getAuthorizationStore();

        List<Permission> permissions = new ArrayList<>();
        permissions.add(new Permission.PermissionBuilder(DEFAULT_RESOURCE, ACTION_ADD,
                "f61a1c240df011e6a1483e1d05defe78", DEFAULT_AUTHORIZATION_STORE)
                .build());
        permissions.add(new Permission.PermissionBuilder(DEFAULT_RESOURCE, new Action("reg", "delete"),
                "64335ff4106211e6a1483e1d05defe78", DEFAULT_AUTHORIZATION_STORE)
                .build());

        authorizationStore.updatePermissionsInRole(DEFAULT_ROLE_ID, DEFAULT_AUTHORIZATION_STORE, permissions);
    }

    @Test(priority = 22)
    public void testUpdatePermissionsInRolePatchValid() throws AuthorizationStoreException {

        AuthorizationStore authorizationStore = realmService.getAuthorizationStore();

        List<Permission> permissions = new ArrayList<>();
        permissions.add(new Permission.PermissionBuilder(DEFAULT_RESOURCE, ACTION_ADD,
                "f61a1c240df011e6a1483e1d05defe78", DEFAULT_AUTHORIZATION_STORE)
                .build());
        permissions.add(new Permission.PermissionBuilder(DEFAULT_RESOURCE, new Action("reg", "delete"),
                "64335ff4106211e6a1483e1d05defe78", DEFAULT_AUTHORIZATION_STORE)
                .build());

        authorizationStore.updatePermissionsInRole(DEFAULT_ROLE_ID, DEFAULT_AUTHORIZATION_STORE, permissions,
                permissions);
    }

    @Test(priority = 23)
    public void testCompleteAuthorizationFlowValid() throws AuthorizationStoreException, IdentityStoreException,
            UserNotFoundException, GroupNotFoundException {

        AuthorizationStore authorizationStore = realmService.getAuthorizationStore();
        IdentityStore identityStore = realmService.getIdentityStore();

        // Add actions.
        Action action1 = authorizationStore.addAction("reg", "test-action1", DEFAULT_AUTHORIZATION_STORE);
        Action action2 = authorizationStore.addAction("reg", "test-action2", DEFAULT_AUTHORIZATION_STORE);

        // Add permissions.
        Permission permission1 = authorizationStore.addPermission(DEFAULT_RESOURCE, action1,
                DEFAULT_AUTHORIZATION_STORE);
        Permission permission2 = authorizationStore.addPermission(DEFAULT_RESOURCE, action2,
                DEFAULT_AUTHORIZATION_STORE);

        List<Permission> permissions = new ArrayList<>();
        permissions.add(permission1);
        permissions.add(permission2);

        // Add roles to user.
        Role role1 = authorizationStore.addRole("test-user-role1", permissions, DEFAULT_AUTHORIZATION_STORE);
        Role role2 = authorizationStore.addRole("test-user-role2", permissions, DEFAULT_AUTHORIZATION_STORE);

        List<Role> userRoles = new ArrayList<>();
        userRoles.add(role1);
        userRoles.add(role2);

        // Get the user.
        User user = identityStore.getUser("admin");
        user.updateRoles(userRoles);

        assertTrue(user.isInRole(role1.getName()));
        assertTrue(user.isInRole(role2.getName()));

        assertTrue(user.isAuthorized(permission1));
        assertTrue(user.isAuthorized(permission2));

        // Add roles to group.
        Role role3 = authorizationStore.addRole("test-group-role1", permissions, DEFAULT_AUTHORIZATION_STORE);
        Role role4 = authorizationStore.addRole("test-group-role2", permissions, DEFAULT_AUTHORIZATION_STORE);

        List<Role> groupRoles = new ArrayList<>();
        groupRoles.add(role3);
        groupRoles.add(role4);

        Group group = identityStore.getGroup("is");

        group.updateRoles(groupRoles);

        assertTrue(group.hasRole(role3.getName()));
        assertTrue(group.hasRole(role4.getName()));

        assertTrue(group.isAuthorized(permission1));
        assertTrue(group.isAuthorized(permission2));
    }
}
