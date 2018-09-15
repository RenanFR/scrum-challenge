<#import "../lib/utils.ftl" as u>
<#import "/spring.ftl" as spring />
<@u.page>
      <div class="row">
        <div class="col-xs-12">
          <div class="box">
            <div class="box-header">
              <h3 class="box-title">Hover Data Table</h3>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
			<table id="example2" class="table table-bordered table-hover">
                <thead>
                <tr>
                  <th><@spring.message "title"/></th>
                  <th><@spring.message "description"/></th>
                  <th><@spring.message "beginDate"/></th>
                  <th><@spring.message "expectedEndDate"/></th>
                  <th><@spring.message "endDate"/></th>
                </tr>
                </thead>
                <tbody>
					<#list quests as q>
					<tr>
					    <td>${q.title}</td>
					    <td>${q.description}</td>
					    <td>${q.beginDate}</td>
					    <td>${q.expectedEndDate}</td>
					    <td>${q.endDate}</td>
					</tr>
					</#list>                
				</tbody>
			</table>
			</div>
		</div>
	</div>			
</@u.page>